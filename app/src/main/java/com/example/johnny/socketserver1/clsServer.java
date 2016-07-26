package com.example.johnny.socketserver1;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Johnny on 21/07/2016.
 */
public class clsServer extends AsyncTask<Void, String, Boolean> {


    private Activity activity;
    private int puerto;

    public clsServer(Activity activity, int puerto) {
        this.activity = activity;
        this.puerto = puerto;
    }




    /*Antes de iniciar el proceso, reiniciamos la barra de progreso*/
    @Override
    protected void onPreExecute() {

    }


    /*
    * El método doInBackground() se ejecuta en un hilo secundario (por tanto no podremos interactuar
    * con la interfaz), pero sin embargo todos los demás se ejecutan en el hilo principal, lo que
    * quiere decir que dentro de ellos podremos hacer referencia directa a nuestros controles de
    * usuario para actualizar la interfaz. Ademas si se llama al metodo publishProgress() este
    * automáticamente ejecuta el onProgressUpdate() que actualizara la interfaz si es necesario
    * */
    @Override
    protected Boolean doInBackground(Void... params) {


        try {
            //Se arranca el servidor
            ServerSocket servidor = new ServerSocket(puerto);
            publishProgress("Escucho el puerto " + puerto);


            for (int numCli = 0; numCli < 3; numCli++) {
                Socket cliente = servidor.accept(); // Crea objeto --- SE BLOQUEA ESPERANDO PETICION?
                publishProgress("Sirvo al cliente " + numCli);
                //medio de respuesta para el cliente.
                DataOutputStream flujo = new DataOutputStream(cliente.getOutputStream());
                flujo.writeUTF("Hola cliente " + numCli);

                //Se cierra el socket
                cliente.close();
            }

            servidor.close();

        } catch (Exception e) {
            publishProgress(e.getMessage());
        }



        /*Se le retorna true a la funcion onPostExecute*/
        return true;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Toast.makeText(activity,values[0], Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPostExecute(Boolean result) {
        /*Tan pronto termine el proceso, se muestra un toast en la activity indincando que termino
        * el proceso*/
        if(result)
            Toast.makeText(activity, "Demasiados clientes por hoy",
                    Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCancelled() {
        /*Si se cancela el proceso se le indica al usuario*/
        Toast.makeText(activity, "Tarea cancelada!",
                Toast.LENGTH_SHORT).show();
    }

}
