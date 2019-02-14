package org.ciaranroche.kang.helpers

import android.content.Intent
import androidx.fragment.app.Fragment
import org.ciaranroche.kang.R

fun showImagePicker(parent: Fragment, id: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    val chooser = Intent.createChooser(intent, R.string.select_vinyl_image.toString())
    parent.startActivityForResult(chooser, id)
}