package com.example.temo.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // ✅ 앱 전체에서 사용 가능하도록 설정
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserRepository(firebaseDB: FirebaseFirestore): UserRepository {
        return UserRepository(firebaseDB)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        firebaseDB: FirebaseFirestore,
        fireStorage: FirebaseStorage
    ): AppRepository {
        return AppRepository(firebaseDB, fireStorage)
    }
}
