package ru.ulstu_team.ulstuschedule.data.local

import io.realm.Realm
import ru.ulstu_team.ulstuschedule.data.model.Favorite

class Favorites {

    fun getFavorites(): List<Favorite> {
        val realm = Realm.getDefaultInstance()
        val favs: List<Favorite> = realm.where(Favorite::class.java).findAll()
        return favs
    }

}