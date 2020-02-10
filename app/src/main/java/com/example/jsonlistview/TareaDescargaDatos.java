package com.example.jsonlistview;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TareaDescargaDatos extends AsyncTask<String, Void, Void>{

        private boolean error = false;
        private ProgressDialog dialog;
        private MainActivity act;

        TareaDescargaDatos(MainActivity act){
            this.act = act;
        }

        /**
         * Método que ejecuta la tarea en segundo plano
         * @param params
         * @return
         */
        @Override
        protected Void doInBackground(String... params) {

            //String url = params[0];
            String resultado;
            JSONObject json;
            JSONArray jsonArray;

            try {
                // Conecta con la URL y obtenemos el fichero con los datos
                URL url = new URL(Constantes.URL);
                Log.d("DAVID", "Antes de URL Sin conexion");
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                Log.d("DAVID", "Despues de URL Recupera conexion");

                // Lee el fichero de datos y genera una cadena de texto como resultado
                BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                Log.d("DAVID", "Se crea buffer");
                StringBuilder sb = new StringBuilder();
                String linea;

                while ((linea = br.readLine()) != null){
                    Log.d("DAVID", linea+"\n");
                    sb.append(linea + "\n");
                }


                conexion.disconnect();
                br.close();
                resultado = sb.toString();
                Log.d("DAVID", "Antes de crear objeto json");
                try{
                    jsonArray = new JSONArray(resultado);
                }catch (Exception e) {
                    Log.d("DAVID", e.toString());
                    jsonArray = new JSONArray();
                }
                Log.d("DAVID", "Se crea objeto json");
                Log.d("DAVID", "VALOR JSON: "+jsonArray.toString());

                String nombre;
                String descripcion;
                String tipo;
                int puntuacion;
                Monumento monumento;
                Log.d("DAVID", "Antes FOR punto antes de recuperar los datos");
                for (int i = 0; i < jsonArray.length(); i++) {
                    nombre = jsonArray.getJSONObject(i).getString("nombre");
                    Log.d("Dentro FOR", "Recupera datos nombre");
                    descripcion = jsonArray.getJSONObject(i).getString("descripcion");
                    Log.d("Dentro FOR", "Recupera datos descripcion");
                    tipo = jsonArray.getJSONObject(i).getString("tipo");
                    Log.d("Dentro FOR", "Recupera datos tipo");
                    puntuacion = jsonArray.getJSONObject(i).getInt("puntuacion");
                    Log.d("Dentro FOR", "Recupera datos puntuacion");

                    monumento = new Monumento(nombre, descripcion, tipo, puntuacion);
                    Log.d("titulo", monumento.toString());

                    MainActivity.listaMonumentos.add(monumento);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
                error = true;
            } catch (JSONException jse) {
                jse.printStackTrace();
                error = true;
            }

            return null;
        }

        /**
         * Método que se ejecuta si la tarea es cancelada antes de terminar
         */
        @Override
        protected void onCancelled() {
            super.onCancelled();
            MainActivity.listaMonumentos = new ArrayList<>();
        }

        /**
         * Método que se ejecuta durante el progreso de la tarea
         * @param progreso
         */
        @Override
        protected void onProgressUpdate(Void... progreso) {
            super.onProgressUpdate(progreso);
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        /**
         * Método ejecutado automáticamente justo antes de lanzar la tarea en segundo plano
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(act);
            dialog.setTitle("Cargando");
            dialog.show();
        }

        /**
         * Método ejecutado automáticamente justo después de terminar la parte en segundo plano
         * Es la parte donde podemos interactuar con el UI para notificar lo sucedido al usuario
         * @param resultado
         */
        @Override
        protected void onPostExecute(Void resultado) {
            super.onPostExecute(resultado);

            if (error) {
                Toast.makeText(act, "Error", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dialog != null)
                dialog.dismiss();

            MainActivity.arrayAdapter.notifyDataSetChanged();
        }
}
