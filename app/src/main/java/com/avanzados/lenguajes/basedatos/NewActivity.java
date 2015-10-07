package com.avanzados.lenguajes.basedatos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by estudiante on 29/09/2015.
 */
public class NewActivity extends Activity{

    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.nueva_activity);

        Toast.makeText(this, "Estamos en nueva Activity",
                Toast.LENGTH_SHORT).show();
    }
}
