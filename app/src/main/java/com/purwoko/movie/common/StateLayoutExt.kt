package com.purwoko.movie.common

import android.widget.Button
import com.erkutaras.statelayout.StateLayout
import com.purwoko.movie.R

fun StateLayout.toEmpty(): StateLayout { this.info(R.layout.sl_base_empty)
    return this.info()
}

fun StateLayout.toError(function: () -> Unit): StateLayout {
    this.info(R.layout.sl_base_error)
    val btnRetry = this.info().findViewById<Button>(R.id.btnRetry)
    btnRetry.setOnClickListener {
        function()
    }
    return this.info()
}

fun StateLayout.toLoading(): StateLayout {
    this.loading(R.layout.sl_base_loading)
    return this.loading()
}

fun StateLayout.toContent(): StateLayout {
    return this.content()
}
