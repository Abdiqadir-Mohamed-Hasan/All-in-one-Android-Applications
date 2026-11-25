package com.example.all_in_onecontroller.features.barodalkaga;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

import com.example.all_in_onecontroller.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.view.GestureDetector;
import android.view.ScaleGestureDetector;

public class SomaliaInteractiveMapView extends View {

    private final List<RegionPath> regionPaths = new ArrayList<>();
    private final List<LabelPoint> labelPoints = new ArrayList<>();
    
    private Paint paintFill;
    private Paint paintStroke;
    private Paint paintSelected;
    private Paint paintText;
    
    private Matrix scaleMatrix;
    private final RectF originalBounds = new RectF();

    private String selectedRegionId = null;
    private String downRegionId = null;

    private OnRegionClickListener listener;
    
    // Zoom and pan properties
    private ScaleGestureDetector scaleDetector;
    private GestureDetector gestureDetector;
    private float scaleFactor = 1.0f;
    private float translateX = 0f;
    private float translateY = 0f;
    
    // Limits
    private static final float MIN_SCALE = 1.0f;
    private static final float MAX_SCALE = 5.0f;

    public interface OnRegionClickListener {
        void onRegionClick(String regionName, String regionId);
    }

    public SomaliaInteractiveMapView(Context context) {
        super(context);
        init();
    }

    public SomaliaInteractiveMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintFill.setColor(Color.parseColor("#E6F4EA"));
        paintFill.setStyle(Paint.Style.FILL);

        paintSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSelected.setColor(Color.parseColor("#059669"));
        paintSelected.setStyle(Paint.Style.FILL);

        paintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintStroke.setColor(Color.WHITE);
        paintStroke.setStyle(Paint.Style.STROKE);
        paintStroke.setStrokeWidth(2f);
        
        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.parseColor("#0F766E"));
        paintText.setTextSize(20f);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setFakeBoldText(true);

        scaleMatrix = new Matrix();

        // Setup Zoom and Pan detectors
        scaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactorChange = detector.getScaleFactor();
                float previousScale = scaleFactor;
                scaleFactor *= scaleFactorChange;
                scaleFactor = Math.max(MIN_SCALE, Math.min(scaleFactor, MAX_SCALE));

                if (previousScale != scaleFactor) {
                    float focusX = detector.getFocusX();
                    float focusY = detector.getFocusY();
                    
                    float adjustedScaleFactor = scaleFactor / previousScale;

                    // Adjust translation to zoom in on the focus point
                    translateX = (translateX - focusX) * adjustedScaleFactor + focusX;
                    translateY = (translateY - focusY) * adjustedScaleFactor + focusY;

                    limitPan();
                    invalidate();
                }
                return true;
            }
        });

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                translateX -= distanceX;
                translateY -= distanceY;
                limitPan();
                invalidate();
                return true;
            }
        });

        loadSvgPaths();
    }
    
    private void limitPan() {
        float viewWidth = getWidth();
        float viewHeight = getHeight();
        
        // The base map is already scaled to fit viewWidth/viewHeight in onSizeChanged.
        // So the current zoomed map size is roughly:
        float mapCurrentWidth = viewWidth * scaleFactor;
        float mapCurrentHeight = viewHeight * scaleFactor;
        
        // Allow the user to pan until the edge of the map reaches the middle of the screen
        float maxTranslateX = viewWidth * 0.5f;
        float minTranslateX = -(mapCurrentWidth - viewWidth * 0.5f);
        
        float maxTranslateY = viewHeight * 0.5f;
        float minTranslateY = -(mapCurrentHeight - viewHeight * 0.5f);

        translateX = Math.max(Math.min(translateX, maxTranslateX), minTranslateX);
        translateY = Math.max(Math.min(translateY, maxTranslateY), minTranslateY);
    }

    public void setOnRegionClickListener(OnRegionClickListener listener) {
        this.listener = listener;
    }

    private void loadSvgPaths() {
        new Thread(() -> {
            try {
                InputStream is = getResources().openRawResource(R.raw.somalia);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(is, "UTF-8");

                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if ("path".equals(xpp.getName())) {
                            String id = xpp.getAttributeValue(null, "id");
                            String name = xpp.getAttributeValue(null, "name");
                            String d = xpp.getAttributeValue(null, "d");

                            if (d != null && name != null) {
                                Path path = PathParser.createPathFromPathData(d);
                                if (path != null) {
                                    regionPaths.add(new RegionPath(id, name, path));
                                }
                            }
                        } else if ("circle".equals(xpp.getName())) {
                            String id = xpp.getAttributeValue(null, "id");
                            String name = xpp.getAttributeValue(null, "class");
                            String cxStr = xpp.getAttributeValue(null, "cx");
                            String cyStr = xpp.getAttributeValue(null, "cy");

                            if (id != null && name != null && cxStr != null && cyStr != null && id.startsWith("SO")) {
                                try {
                                    float cx = Float.parseFloat(cxStr);
                                    float cy = Float.parseFloat(cyStr);
                                    
                                    LabelPoint existing = null;
                                    for (LabelPoint lp : labelPoints) {
                                        if (lp.id.equals(id)) {
                                            existing = lp;
                                            break;
                                        }
                                    }
                                    if (existing != null) {
                                        existing.originalX = cx;
                                        existing.originalY = cy;
                                    } else {
                                        labelPoints.add(new LabelPoint(id, name, cx, cy));
                                    }
                                } catch (NumberFormatException ignored) {}
                            }
                        }
                    }
                    eventType = xpp.next();
                }
                is.close();

                // Compute bounding box
                for (RegionPath rp : regionPaths) {
                    RectF bounds = new RectF();
                    rp.originalPath.computeBounds(bounds, true);
                    originalBounds.union(bounds);
                }

                postInvalidate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (originalBounds.isEmpty() || w == 0 || h == 0) return;

        float scaleX = w / originalBounds.width();
        float scaleY = h / originalBounds.height();
        float scale = Math.min(scaleX, scaleY) * 0.95f; // 5% padding

        scaleMatrix.reset();
        scaleMatrix.postTranslate(-originalBounds.left, -originalBounds.top);
        scaleMatrix.postScale(scale, scale);

        float dx = (w - (originalBounds.width() * scale)) / 2f;
        float dy = (h - (originalBounds.height() * scale)) / 2f;
        scaleMatrix.postTranslate(dx, dy);

        for (RegionPath rp : regionPaths) {
            rp.scaledPath.set(rp.originalPath);
            rp.scaledPath.transform(scaleMatrix);
            
            RectF bounds = new RectF();
            rp.scaledPath.computeBounds(bounds, true);
            rp.touchRegion.setPath(rp.scaledPath, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
        }

        float[] pts = new float[2];
        for (LabelPoint lp : labelPoints) {
            pts[0] = lp.originalX;
            pts[1] = lp.originalY;
            scaleMatrix.mapPoints(pts);
            lp.x = pts[0];
            lp.y = pts[1];
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (originalBounds.isEmpty() && !regionPaths.isEmpty()) {
            onSizeChanged(getWidth(), getHeight(), 0, 0);
        }

        canvas.save();
        
        // Apply zoom and pan transformations
        canvas.translate(translateX, translateY);
        canvas.scale(scaleFactor, scaleFactor);

        for (RegionPath rp : regionPaths) {
            boolean isSelected = rp.id != null && rp.id.equals(selectedRegionId);
            canvas.drawPath(rp.scaledPath, isSelected ? paintSelected : paintFill);
            canvas.drawPath(rp.scaledPath, paintStroke);
        }
        canvas.restore();
        
        // Draw labels dynamically ONLY when zoomed in
        if (scaleFactor >= 1.8f) {
            paintText.setTextSize(26f); 
            Paint.FontMetrics fm = paintText.getFontMetrics();
            float yOffset = (fm.descent - fm.ascent) / 2 - fm.descent;

            for (LabelPoint lp : labelPoints) {
                if (lp.x > 0 && lp.y > 0) {
                    float screenX = lp.x * scaleFactor + translateX;
                    float screenY = lp.y * scaleFactor + translateY;
                    
                    boolean isSelected = lp.id != null && lp.id.equals(selectedRegionId);
                    paintText.setColor(isSelected ? Color.WHITE : Color.parseColor("#0F766E"));
                    
                    canvas.drawText(lp.name, screenX, screenY + yOffset, paintText);
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Disallow the NestedScrollView from intercepting touch events
        // so the map can properly consume panning and zooming gestures.
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        // Pass event to detectors for zooming and panning
        scaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        
        // Calculate logical touch point considering zoom and pan
        int logicalX = (int) ((event.getX() - translateX) / scaleFactor);
        int logicalY = (int) ((event.getY() - translateY) / scaleFactor);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downRegionId = null;
                for (RegionPath rp : regionPaths) {
                    if (rp.touchRegion.contains(logicalX, logicalY)) {
                        downRegionId = rp.id;
                        selectedRegionId = rp.id;
                        invalidate();
                        return true; 
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                if (downRegionId != null) {
                    for (RegionPath rp : regionPaths) {
                        if (rp.touchRegion.contains(logicalX, logicalY) && rp.id.equals(downRegionId)) {
                            if (listener != null) {
                                listener.onRegionClick(rp.name, rp.id);
                            }
                            break;
                        }
                    }
                    downRegionId = null;
                    selectedRegionId = null;
                    invalidate();
                    return true;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                downRegionId = null;
                selectedRegionId = null;
                invalidate();
                break;
        }
        return true;
    }

    private static class RegionPath {
        String id;
        String name;
        Path originalPath;
        Path scaledPath = new Path();
        Region touchRegion = new Region();

        RegionPath(String id, String name, Path originalPath) {
            this.id = id;
            this.name = name;
            this.originalPath = originalPath;
        }
    }

    private static class LabelPoint {
        String id;
        String name;
        float originalX;
        float originalY;
        float x;
        float y;

        LabelPoint(String id, String name, float originalX, float originalY) {
            this.id = id;
            this.name = name;
            this.originalX = originalX;
            this.originalY = originalY;
        }
    }
}
