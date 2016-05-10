package com.example.efaa.iee;

/**
 * Created by Neri Ortez on 09/05/2016.
 */

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    AdaptadorArrayClase adaptador;
    ListView lista;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager lManager;
    ClaseRecyclerAdaptador cAdaptador;
    SQLiteDatabase dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);
    public View.OnClickListener checkBoxClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dob = SQLiteDatabase.openOrCreateDatabase("/sdcard/UNAH_IEE/data.sqlite", null);

            CheckBox checkBox = (CheckBox) view.findViewById(R.id.Nombre_de_Clase);
            LinearLayout e = (LinearLayout) view.getParent();
            TextView codigo = (TextView) view.findViewById(R.id.Codigo);
            String cod = (String) codigo.getText();
            String dato = null;

            if (checkBox.isChecked()) {
                dato = "1";
            } else {
                dato = "0";
            }
            String result = new dataSource().insertarUnoOCero(dob, cod, dataSource.Columnas.CURSADA, dato,
                    new dataSource().queryCrearClase(dob, cod, view.getContext()), view.getContext());
            if (result == "-1") {
                checkBox.setChecked(true);
            }

//        ClassFragment fra = getShe
            else if ((result != "0" || result != "1")
                    && getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

            }
            dob.releaseReference();


        }
    };
    String COLUMNA;


    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public void run() {
        //Conseguimos el array
        ArrayList array = new ArrayList();
        array = dataSource.queryPasadasODisponibles(dob, COLUMNA, "1", getContext());

        //Creamos el adaptador para ese array
//       adaptador = new AdaptadorArrayClase(getContext(), array);
        cAdaptador = new ClaseRecyclerAdaptador(array);
        recyclerView.setAdapter(cAdaptador);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflar la pestaña con el fragmento que contiene el ListView o el RecyclerView
//        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        View rootView = inflater.inflate(R.layout.reciclador_tab, container, false);

        //Conseguir el listView o RecyclerView
//        lista = (ListView) rootView.findViewById(R.id.listViewFragment);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.reciclador);
        recyclerView.setHasFixedSize(true);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        //Establecer el tipo de vista: Cursadas, Disponibles o especial
        String DISPONIBLE = dataSource.Columnas.DISPONIBLE;
        String columna = DISPONIBLE;
        String CURSADA = dataSource.Columnas.CURSADA;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                columna = CURSADA;
        }
        COLUMNA = columna;

//        String columns[] = new String[]{"_id", dataSource.Columnas.NOMBRE, dataSource.Columnas.CODIGO,
//                dataSource.Columnas.PORcURSAR, CURSADA, DISPONIBLE,
//                dataSource.Columnas.UV, dataSource.Columnas.INDICE};


//        String selection = columna + " = " + "1" + " ";//WHERE author = ?


        //Establecemos adaptadores
//        lista.setAdapter(adapter);
        lManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(lManager);
        recyclerView.setAdapter(cAdaptador);


        //Creamos los listener para clicks
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        };
        View.OnClickListener escucha = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                return;
                            }
                        }).show();
            }
        };


        //Seteamos esos escuchas
//        lista.setOnItemClickListener(listener);
        recyclerView.setOnClickListener(escucha);
        return rootView;
    }


    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, )
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     * <p/>
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     * <p/>
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
