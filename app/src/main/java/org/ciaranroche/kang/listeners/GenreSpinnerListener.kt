package org.ciaranroche.kang.listeners

import android.util.Log
import android.view.View
import android.widget.AdapterView

class GenreSpinnerListener : AdapterView.OnItemSelectedListener{

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        Log.i("boop", parent?.getItemAtPosition(pos).toString())
    }

}