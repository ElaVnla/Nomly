package com.w3itexperts.ombe.component;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class CustomLineChartView extends View {

    private Paint linePaint;
    private Paint fillPaint;
    private Paint axisPaint;
    private Paint textPaint;

    // Sample data points
    private float[] dataPoints = {4.5f, 5.0f, 3.0f, 6.0f, 5.5f, 6.5f, 4.0f, 5.5f};
    private String[] xLabels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug"};

    public CustomLineChartView(Context context) {
        super(context);
        init();
    }

    public CustomLineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomLineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Initialize Paint objects for drawing
        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#4CAF50"));
        linePaint.setStrokeWidth(8f);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);

        fillPaint = new Paint();
        fillPaint.setColor(Color.parseColor("#4CAF50"));
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAlpha(100); // Set transparency for fill

        axisPaint = new Paint();
        axisPaint.setColor(Color.GRAY);
        axisPaint.setStrokeWidth(2f);
        axisPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(32f);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float padding = 60f;

        // Draw X and Y axes
        canvas.drawLine(padding, height - padding, width - padding, height - padding, axisPaint); // X-axis
        canvas.drawLine(padding, padding, padding, height - padding, axisPaint); // Y-axis

        // Draw X-axis labels
        float xInterval = (width - 2 * padding) / (xLabels.length - 1);
        for (int i = 0; i < xLabels.length; i++) {
            float x = padding + i * xInterval;
            canvas.drawText(xLabels[i], x, height - padding + 40, textPaint);
        }

        // Draw line chart and filled area
        Path linePath = new Path();
        Path fillPath = new Path();

        float maxDataPoint = 7.0f; // Example maximum Y value for scaling
        float yInterval = (height - 2 * padding) / maxDataPoint;

        linePath.moveTo(padding, height - padding - (dataPoints[0] * yInterval));
        fillPath.moveTo(padding, height - padding);
        fillPath.lineTo(padding, height - padding - (dataPoints[0] * yInterval));

        for (int i = 1; i < dataPoints.length; i++) {
            float x = padding + i * xInterval;
            float y = height - padding - (dataPoints[i] * yInterval);
            linePath.lineTo(x, y);
            fillPath.lineTo(x, y);
        }

        fillPath.lineTo(width - padding, height - padding); // Close the fill path
        fillPath.close();

        // Draw filled area
        canvas.drawPath(fillPath, fillPaint);

        // Draw the line chart
        canvas.drawPath(linePath, linePaint);
    }
}
