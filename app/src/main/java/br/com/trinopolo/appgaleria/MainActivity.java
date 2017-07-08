package br.com.trinopolo.appgaleria;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    final private String[] imageNames = new String[] {
        "logo1.png", "logo2.jpg", "logo3.jpg", "logo4.png"
    };

    private int posicaoAtual = 0;

    private Bitmap[] imagens = new Bitmap[4];

    private ImageView imageView;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        loadImages();
        posicaoAtual = getPosicao();
        mostraImagem(posicaoAtual);
        salvarPosicao(posicaoAtual);
    }

    private void salvarPosicao(int posicao) {

        getSharedPreferences("galeria", MODE_PRIVATE).edit()
                .putInt("posicao", posicao).commit();
    }

    private int getPosicao() {
        return getSharedPreferences("galeria", MODE_PRIVATE)
                .getInt("posicao", 0);
    }

    private void mostraImagem(int posicao) {

        imageView.setImageBitmap(imagens[posicao]);
        tvInfo.setText(String.valueOf(posicaoAtual));
        salvarPosicao(posicao);
    }

    private void loadImages() {

        try {
            for (int i = 0; i < imageNames.length; i++) {
                InputStream is = getAssets().open(imageNames[i]);
                imagens[i] = BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup() {

        imageView = (ImageView) findViewById(R.id.imageView);
        tvInfo = (TextView) findViewById(R.id.txInfo);
    }

    public void btNextOnClick(View view) {
        posicaoAtual++;
        if(posicaoAtual > 3) { posicaoAtual = 0; }
        mostraImagem(posicaoAtual);
    }

    public void btBackOnClick(View view) {
        posicaoAtual--;
        if(posicaoAtual < 0) { posicaoAtual = imagens.length - 1; }
        mostraImagem(posicaoAtual);
    }
}
