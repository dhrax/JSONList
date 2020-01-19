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
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                // Lee el fichero de datos y genera una cadena de texto como resultado
                BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String linea;

                while ((linea = br.readLine()) != null)
                    sb.append(linea + "\n");

                conexion.disconnect();
                br.close();
                resultado = sb.toString();

                json = new JSONObject(resultado);
                jsonArray = json.getJSONArray("features");

                String titulo;
                String link;
                String coordenadas;
                Monumento monumento;
                for (int i = 0; i < jsonArray.length(); i++) {
                    titulo = jsonArray.getJSONObject(i).getJSONObject("properties").getString("title");
                    link = jsonArray.getJSONObject(i).getJSONObject("properties").getString("link");
                    coordenadas = jsonArray.getJSONObject(i).getJSONObject("geometry").getString("coordinates");
                    coordenadas = coordenadas.substring(1, coordenadas.length() - 1);
                    String latlong[] = coordenadas.split(",");
                    monumento = new Monumento(titulo, link, Float.parseFloat(latlong[0]), Float.parseFloat(latlong[1]));
                    Log.d("titulo", monumento.toString());
                    /*monumento.setTitulo(titulo);
                    monumento.setLink(link);
                    monumento.setLatitud(Float.parseFloat(latlong[0]));
                    monumento.setLongitud(Float.parseFloat(latlong[1]));*/

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
