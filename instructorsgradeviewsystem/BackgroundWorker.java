package com.example.instructorsgradeviewsystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    ProgressBar progress;
    String status, student_num;
    BackgroundWorker (Context ctx, ProgressBar progressBar){
        context = ctx;
        progress = progressBar;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
//        String login_url = "https://ocinstructorgvs.000webhostapp.com/login.php";
//        String adduser_url = "https://ocinstructorgvs.000webhostapp.com/adduser.php";
//        String studentlogin_url = "https://ocinstructorgvs.000webhostapp.com/studentlogin.php";
//        String studentreg_url = "https://ocinstructorgvs.000webhostapp.com/studentreg.php";
//        String grades_url ="https://ocinstructorgvs.000webhostapp.com/uploadgrades.php";

        String login_url = "https://geloyujin.000webhostapp.com/login.php";
        String adduser_url = "https://geloyujin.000webhostapp.com/adduser.php";
        String studentlogin_url = "https://geloyujin.000webhostapp.com/studentlogin.php";
        String studentreg_url = "https://geloyujin.000webhostapp.com/studentreg.php";
        String grades_url ="https://geloyujin.000webhostapp.com/uploadgrades.php";

        if(type.equals("login")){
            try {
                String user_name = params[1];
                String pass_word = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(user_name, "UTF-8")+"&"+URLEncoder.encode("pass_word", "UTF-8")+"="+URLEncoder.encode(pass_word, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line="";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                status = "1";
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("adduser")) {
            try {
                String user_name = params[1];
                String pass_word = params[2];
                URL url = new URL(adduser_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(user_name, "UTF-8")+"&"+URLEncoder.encode("pass_word", "UTF-8")+"="+URLEncoder.encode(pass_word, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line="";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                status = "2";
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (type.equals("studentlogin")) {
            try {
                String studentNum = params[1];
                String pWord = params [2];
                URL url = new URL(studentlogin_url);
                student_num = studentNum;
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("studentNum", "UTF-8")+"="+URLEncoder.encode(studentNum, "UTF-8")+"&"+URLEncoder.encode("pWord", "UTF-8")+"="+URLEncoder.encode(pWord, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line="";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                status = "3";
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (type.equals("StudentReg")) {
            try {
                String studentName = params[1];
                String studentNum = params[2];
                String pWord = params [3];
                URL url = new URL(studentreg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("studentName", "UTF-8")+"="+URLEncoder.encode(studentName, "UTF-8")+"&"+URLEncoder.encode("studentNum", "UTF-8")+"="+URLEncoder.encode(studentNum, "UTF-8")+"&"+URLEncoder.encode("pWord", "UTF-8")+"="+URLEncoder.encode(pWord, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line="";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                status = "4";
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (type.equals("masterlist")) {
            try {

                String t1 = params[1];
                String submit = params[2];
                Log.d("t1", t1);
                Log.d("submit", submit);
                grades_url ="https://geloyujin.000webhostapp.com/masterlist_ths.php";


                URL url = new URL(grades_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("t1", "UTF-8")+"="+URLEncoder.encode(t1, "UTF-8")+"&"+URLEncoder.encode("submit", "UTF-8")+"="+URLEncoder.encode(submit, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line="";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                status = "5";
                Log.d("t1", t1);
                Log.d("submit", submit);
                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Logging In");

    }

    @Override
    protected void onPostExecute(String result) {
        //instructor login
        if(status.equals("1")){
            alertDialog.setMessage(result);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    progress.setVisibility(View.INVISIBLE);
                    dialog.dismiss();
                    if (result.equals("Login Success")) {
                        Intent intent = new Intent(context, SubSelect.class);
                        context.startActivity(intent);
                    }
                }
            });
            alertDialog.show();

        }

        //add user
        else if (status.equals("2")) {
            progress.setVisibility(View.INVISIBLE);
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }

        //3, studentlogin
        else if (status.equals("3")){
            String a = student_num;
            alertDialog.setMessage(result);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    progress.setVisibility(View.INVISIBLE);
                    if (result.equals("Login Success")) {
                        Intent intent = new Intent (context, WelcomePage.class);
                        intent.putExtra("name", a);
                        context.startActivity(intent);
                    }
                }
            });
            alertDialog.show();

        }

        //student reg
        else if (status.equals("4")){
            progress.setVisibility(View.INVISIBLE);
            alertDialog.setMessage(result);
            alertDialog.show();
        }

        else if (status.equals("5")){
            progress.setVisibility(View.INVISIBLE);
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}


