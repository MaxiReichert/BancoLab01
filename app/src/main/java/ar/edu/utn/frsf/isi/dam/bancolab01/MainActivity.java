package ar.edu.utn.frsf.isi.dam.bancolab01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import ar.edu.utn.frsf.dam.bancolab01.modelo.Cliente;
import ar.edu.utn.frsf.dam.bancolab01.modelo.PlazoFijo;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private PlazoFijo pf;
    private Cliente cliente;
    private TextView tvMail;
    private EditText edtMail;
    private TextView tvCuit;
    private EditText edtCuit;
    private TextView tvMoneda;
    private RadioGroup optMoneda;
    private RadioButton optDolar;
    private RadioButton optPeso;
    private TextView tvMonto;
    private EditText edtMonto;
    private TextView tvDias;
    private SeekBar seekDias;
    private TextView tvDiasSelec;
    private TextView tvRendimiento;
    private TextView tvAvisarMail;
    private Switch swAvisarMail;
    private ToggleButton togAcreditar;
    private CheckBox chkTerminos;
    private Button btnHacerPF;
    private TextView edtMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pf= new PlazoFijo(getResources().getStringArray(R.array.tasas));
        cliente= new Cliente();
        tvMail=(TextView) findViewById(R.id.tvMail);
        edtMail=(EditText) findViewById(R.id.edtMail);
        tvCuit=(TextView) findViewById(R.id.tvCuit);
        edtCuit=(EditText) findViewById(R.id.edtCuit);
        tvMoneda= (TextView) findViewById(R.id.tvMoneda);
        optMoneda= (RadioGroup) findViewById(R.id.optMoneda);
        optDolar= (RadioButton) findViewById(R.id.optDolar);
        optPeso= (RadioButton) findViewById(R.id.optPeso);
        tvMonto= (TextView) findViewById(R.id.tvMonto);
        edtMonto= (EditText) findViewById(R.id.edtMonto);
        tvDias= (TextView) findViewById(R.id.tvDias);
        seekDias= (SeekBar) findViewById(R.id.seekDias);
        tvDiasSelec= (TextView) findViewById(R.id.tvDiasSelec);
        tvRendimiento= (TextView) findViewById(R.id.tvRendimiento);
        tvAvisarMail= (TextView) findViewById(R.id.tvAvisarMail);
        swAvisarMail= (Switch) findViewById(R.id.swAvisarMail);
        togAcreditar= (ToggleButton) findViewById(R.id.togAcreditar);
        chkTerminos= (CheckBox) findViewById(R.id.chkTerminos);
        btnHacerPF= (Button) findViewById(R.id.btnHacerPF);
        edtMensaje= (TextView) findViewById(R.id.edtMensaje);

        optDolar.setActivated(false);
        optPeso.setActivated(true);
        swAvisarMail.setActivated(false);
        chkTerminos.setActivated(false);
        btnHacerPF.setEnabled(false);


        seekDias.setMax(180);
        seekDias.setProgress(10);
        seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDiasSelec.setText(progress + " dias de plazo");
                pf.setDias(progress);
                pf.setMonto(Double.parseDouble(edtMonto.getText().toString()));
                Double intereses=pf.intereses();
                pf.setMonto(pf.getMonto()+intereses);
                tvRendimiento.setText(pf.getMonto().toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        chkTerminos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    btnHacerPF.setEnabled(true);
                }
                else{
                    btnHacerPF.setEnabled(false);
                    String mensaje="Es obligatorio aceptar las condiciones";
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnHacerPF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtMensaje.setText("");
                String mensajeToast="campos invalidos";
                String mensaje="campo Mail no debe estar vacio\n el CUIT/CUIL no debe estar vacio\n" +
                        "el monto debe ser mayor a 0\n la cantidad de dias debe ser mayor a 10";
                if(edtMail.getText().toString().isEmpty() || edtCuit.toString().isEmpty() ||
                        Double.parseDouble(edtMonto.getText().toString())<0.0 || seekDias.getProgress()<=10){
                    Toast.makeText(getApplicationContext(),mensajeToast,Toast.LENGTH_SHORT).show();
                    edtMensaje.setText(mensaje);
                }
                else{
                    edtMensaje.setText(pf.toString());
                }
            }
        });

    }
}
