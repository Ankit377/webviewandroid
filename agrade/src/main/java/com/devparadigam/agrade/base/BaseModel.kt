package com.devparadigam.agrade.base

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable

open class BaseModel(
     var _isSelected: Boolean,
     var _position: Int,
     var _my_value: String
) : BaseObservable(), Parcelable {

    constructor() : this(false, 0, "") {

    }

//    @get:Bindable
//    var isSelected: Boolean = false
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.selected)
//        }

    /* var isSelected: Boolean
         @Bindable get() = _isSelected
         set(value) {
             _isSelected = value
             notifyPropertyChanged(BR.selected)
         }*/

    open var position = 0
        get() = _position

    var my_value: String
        get() = _my_value
        set(value) {
            _my_value = value
        }

    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString()?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (_isSelected) 1 else 0)
        parcel.writeInt(_position)
        parcel.writeString(_my_value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<com.devparadigam.agrade.base.BaseModel> {
        override fun createFromParcel(parcel: Parcel): com.devparadigam.agrade.base.BaseModel {
            return com.devparadigam.agrade.base.BaseModel(parcel)
        }

        override fun newArray(size: Int): Array<com.devparadigam.agrade.base.BaseModel?> {
            return arrayOfNulls(size)
        }
    }

}