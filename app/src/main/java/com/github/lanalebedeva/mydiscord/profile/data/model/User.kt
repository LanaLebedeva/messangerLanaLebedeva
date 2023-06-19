package com.github.lanalebedeva.mydiscord.profile.data.model

import android.os.Parcel
import android.os.Parcelable
import com.github.lanalebedeva.mydiscord.R
import ru.tinkoff.mobile.tech.ti_recycler.base.ViewTyped


data class User(
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String? = null,
    val statusIdle: Boolean = false,
    val statusOnline: Boolean = false,
    override val viewType: Int = R.layout.user_item,
    override val uid: String = id.toString()
) : ViewTyped, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(avatar)
        parcel.writeByte(if (statusIdle) 1 else 0)
        parcel.writeByte(if (statusOnline) 1 else 0)
        parcel.writeInt(viewType)
        parcel.writeString(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
