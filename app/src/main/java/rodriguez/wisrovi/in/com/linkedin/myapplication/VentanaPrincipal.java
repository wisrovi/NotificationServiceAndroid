package rodriguez.wisrovi.in.com.linkedin.myapplication;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VentanaPrincipal extends AppCompatActivity {

    TextView textView;
    Button boton1, boton2, boton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal);

        textView = findViewById(R.id.txt1);


        boton1 = findViewById(R.id.button);
        boton2 = findViewById(R.id.button2);
        boton3 = findViewById(R.id.button3);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SendDataService
            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //iniciar servicio
            }
        });

        boton3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //configurar datos para sql

            }
        });
    }



}
