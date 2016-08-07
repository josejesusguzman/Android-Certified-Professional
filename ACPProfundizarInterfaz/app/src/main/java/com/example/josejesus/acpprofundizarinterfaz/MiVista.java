package com.example.josejesus.acpprofundizarinterfaz;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by josejesus on 6/24/2016.
 */
public class MiVista extends View {

    private int colorCirculo, colorEtiqueta;
    private String textoCirculo;
    private Paint paint; //Crear el objeto que se añadiran las caracteristicas del View
    private int radio;

    public MiVista(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.estilos, 0, 0); //Poder utilizar estilos editables

        try {
            textoCirculo =  typedArray.getString(R.styleable.estilos_etiquetaCirculo);
            colorCirculo = typedArray.getInt(R.styleable.estilos_colorCirculo, 0);//Si no se encuentra se pasa algo por defecto
            colorEtiqueta = typedArray.getInt(R.styleable.estilos_colorEtiqueta, 0);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//Definir las caracteristicas delcirculo

        int viewWidth = this.getMeasuredWidth() / 2;
        int viewHeight = this.getMeasuredHeight() / 2;

        radio = 0;

        if (viewWidth > viewHeight) {
            radio = viewHeight - 10;
        } else {
            radio = viewWidth - 10;
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true); //Permite crear objetos View más esteticos
        paint.setColor(colorCirculo);

        canvas.drawCircle(viewWidth, viewHeight, radio, paint);

        paint.setColor(colorEtiqueta);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);

        canvas.drawText(textoCirculo, viewWidth, viewHeight, paint);


    }

    public void setColorCirculo(int colorCirculo) {
        this.colorCirculo = colorCirculo;
        invalidate(); //Se redibujaran los elementos
        requestLayout();
    }

    public void setColorEtiqueta(int colorEtiqueta) {
        this.colorEtiqueta = colorEtiqueta;
        invalidate();
        requestLayout();
    }

    public void setTextoCirculo(String textoCirculo) {
        this.textoCirculo = textoCirculo;
        invalidate();
        requestLayout();
    }
}
