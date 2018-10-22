package com.hencoder.hencoderpracticedraw2.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice01LinearGradientView extends View {
    private Paint paint;

    public Practice01LinearGradientView(Context context) {
        this(context, null);
    }

    public Practice01LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Practice01LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        /**
         * 抗锯齿：
         *      未开启抗锯齿的图形，所有像素都是同样的黑色，而开启了抗锯齿的图形，边缘的颜色被略微改变了。这种改变可以让人眼有边缘平滑的感觉，但从某种角度讲，它也造成了图形的颜色失真。
         */
        /**
         * 抖动：
         *      图像从较高色彩深度（即可用的颜色数）向较低色彩深度的区域绘制时，在图像中有意地插入噪点，通过有规律地扰乱图像来让图像对于肉眼更加真实的做法。
         *      在实际的应用场景中，抖动更多的作用是在图像降低色彩深度绘制时，避免出现大片的色带与色块。
         */
        /**
         * 双线性过滤：
         *      图像在放大绘制的时候，默认使用的是最近邻插值过滤，这种算法简单，但会出现马赛克现象；而如果开启了双线性过滤，就可以让结果图像显得更加平滑。
         */
        // 抗锯齿 | 抖动 | 双线性过滤
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG);// 可以初始化时直接设置，也可以调用对应的set方法设置
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 用 Paint.setShader(shader) 设置一个 LinearGradient
        // LinearGradient 的参数：坐标：(100, 100) 到 (500, 500) ；颜色：#E91E63 到 #2196F3

        // FIXME: 2018/9/25 onDraw中不建议new对象，这里只是作为演示代码
//        paint.setARGB();
        Shader shader = new LinearGradient(getWidth() / 2 - getHeight() / 4, getHeight() / 2, getWidth() / 2 + getHeight() / 4, getHeight() / 2, Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        paint.setShader(shader);

        // 如果所画的图形如果和shader没有重合，那就只有图形内部的与shader有交集的部分才有shader效果
        // shader是作用在paint上的，只有用这个paint画出图形的区域才有这个shader的效果
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getHeight() / 4, paint);
    }
}
