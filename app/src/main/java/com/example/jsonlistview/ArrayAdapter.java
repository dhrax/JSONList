package com.example.jsonlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArrayAdapter extends BaseAdapter {

    private Context contexto;
    private ArrayList<Monumento> monumentos;
    private LayoutInflater layoutinflater;

    ArrayAdapter(Context contexto, ArrayList<Monumento> monumentos){
        this.contexto = contexto;
        this.monumentos = monumentos;
        layoutinflater = LayoutInflater.from(contexto);
    }

    static class ViewHolder{
        TextView titulo;
        TextView link;
        TextView latitud;
        TextView longitud;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;

        if(convertView == null){
            convertView = layoutinflater.inflate(R.layout.monumento_item, null);
            viewholder = new ViewHolder();
            viewholder.titulo = convertView.findViewById(R.id.titulo);
            viewholder.link = convertView.findViewById(R.id.link);
            viewholder.latitud = convertView.findViewById(R.id.latitud);
            viewholder.longitud = convertView.findViewById(R.id.longitud);
            convertView.setTag(viewholder);

        }else
            viewholder = (ViewHolder) convertView.getTag();

        Monumento monumento = monumentos.get(position);
        viewholder.titulo.setText(monumento.getTitulo());
        viewholder.link.setText(monumento.getLink());
        viewholder.latitud.setText(String.valueOf(monumento.getLatitud()));
        viewholder.longitud.setText(String.valueOf(monumento.getLongitud()));

        return convertView;
    }

    @Override
    public int getCount() {
        return monumentos.size();
    }

    @Override
    public Object getItem(int position) {
        return monumentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
