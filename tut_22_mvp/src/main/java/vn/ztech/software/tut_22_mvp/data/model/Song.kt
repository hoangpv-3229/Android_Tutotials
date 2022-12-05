package vn.ztech.software.tut_22_mvp.data.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Song (val name: String, val author: String, val uri: Uri): Parcelable
