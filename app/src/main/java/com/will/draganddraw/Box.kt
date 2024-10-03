package com.will.draganddraw

import android.graphics.PointF
import android.os.Parcel
import android.os.Parcelable

class Box(val start: PointF): Parcelable {
    var end: PointF = start

    val left: Float
        get() = Math.min(start.x, end.x)

    val right: Float
        get() = Math.max(start.x, end.x)

    val top: Float
        get() = Math.min(start.y, end.y)

    val bottom: Float
        get() = Math.max(start.y, end.y)

    // Parcelable implementation
//    constructor(parcel: Parcel) : this(
//        parcel.readParcelable(PointF::class.java.classLoader) ?: PointF(0f, 0f)
//    ) {
//        end = parcel.readParcelable(PointF::class.java.classLoader) ?: PointF(0f, 0f)
//    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(start, flags)
        dest.writeParcelable(end, flags)
    }

//    companion object CREATOR : Parcelable.Creator<Box> {
//        override fun createFromParcel(parcel: Parcel): Box {
//            return Box(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Box?> {
//            return arrayOfNulls(size)
//        }
//    }
}