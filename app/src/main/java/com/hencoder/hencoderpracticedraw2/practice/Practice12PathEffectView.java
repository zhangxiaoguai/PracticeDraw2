package com.hencoder.hencoderpracticedraw2.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice12PathEffectView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();

    CornerPathEffect cornerPathEffect;
    DiscretePathEffect discretePathEffect;
    DashPathEffect dashPathEffect;
    PathDashPathEffect pathDashPathEffect;
    SumPathEffect sumPathEffect;
    ComposePathEffect composePathEffect;
    ComposePathEffect composePathEffect2;

    public Practice12PathEffectView(Context context) {
        super(context);
        init();
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice12PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        resetPaint();

        path.moveTo(50, 100);
        path.rLineTo(50, 100);
        path.rLineTo(80, -150);
        path.rLineTo(100, 100);
        path.rLineTo(70, -120);
        path.rLineTo(150, 80);

        initPathEffect();
    }

    private void initPathEffect() {
        cornerPathEffect = new CornerPathEffect(20);

        discretePathEffect = new DiscretePathEffect(20, 5);

        dashPathEffect = new DashPathEffect(new float[]{20, 10, 5, 10}, 0);

        Path dashPath = new Path();
        dashPath.moveTo(15, 0);
        dashPath.lineTo(30, (float) (15 * Math.sqrt(3)));
        dashPath.lineTo(0, (float) (15 * Math.sqrt(3)));
        dashPath.close();
        pathDashPathEffect = new PathDashPathEffect(dashPath, 40, 0, PathDashPathEffect.Style.TRANSLATE);

        sumPathEffect = new SumPathEffect(discretePathEffect, dashPathEffect);

        composePathEffect = new ComposePathEffect(dashPathEffect, discretePathEffect);

        // 和dashPath有什么区别？
        composePathEffect2 = new ComposePathEffect(discretePathEffect, dashPathEffect);

        /**
         * 注意： PathEffect 在有些情况下不支持硬件加速，需要关闭硬件加速才能正常使用：
         *
         * 1.Canvas.drawLine() 和 Canvas.drawLines() 方法画直线时，setPathEffect() 是不支持硬件加速的；
         * 2.PathDashPathEffect 对硬件加速的支持也有问题，所以当使用 PathDashPathEffect 的时候，最好也把硬件加速关了。
         */
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.setPathEffect() 来设置不同的 PathEffect

        // 第一处：CornerPathEffect
        paint.setPathEffect(cornerPathEffect);
        canvas.drawPath(path, paint);

        // 这里的save和restore主要是为了防止canvas.translate方法导致坐标变化
        // 同样，对于canvas.rotate方法变化坐标轴位置之后也可以使用save和restore来恢复之前的状态
        canvas.save();
        // 平移
        canvas.translate(500, 0);
        resetPaint();
        // 第二处：DiscretePathEffect
        paint.setPathEffect(discretePathEffect);
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 200);
        resetPaint();
        // 第三处：DashPathEffect
        paint.setPathEffect(dashPathEffect);
        canvas.drawPath(path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 200);
        resetPaint();
        // 第四处：PathDashPathEffect
        paint.setPathEffect(pathDashPathEffect);
        canvas.drawPath(this.path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 400);
        resetPaint();
        // 第五处：SumPathEffect
        paint.setPathEffect(sumPathEffect);
        canvas.drawPath(this.path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 400);
        resetPaint();
        // 第六处：ComposePathEffect
        paint.setPathEffect(composePathEffect);
        canvas.drawPath(this.path, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 600);
        resetPaint();
        // 第七处：ComposePathEffect
        paint.setPathEffect(composePathEffect2);
        canvas.drawPath(this.path, paint);
        canvas.restore();

        // 取消path绘制效果
        paint.setPathEffect(null);
    }

    private void resetPaint() {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        paint.setStyle(Paint.Style.STROKE);
    }
}
