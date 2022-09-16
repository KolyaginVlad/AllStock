package ru.kolyagin.allstock.data.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kolyagin.allstock.Constants
import ru.kolyagin.allstock.Constants.BASE_URL
import ru.kolyagin.allstock.data.mapper.*
import ru.kolyagin.allstock.data.remote.retrofit.FinnApi
import ru.kolyagin.allstock.data.repositories.StockRepository
import ru.kolyagin.allstock.data.repositories.StockRepositoryImpl
import ru.kolyagin.allstock.data.repositories.SymbolRepository
import ru.kolyagin.allstock.data.repositories.SymbolRepositoryImpl
import ru.kolyagin.allstock.data.room.Database
import ru.kolyagin.allstock.data.room.dao.SymbolDao

val dataModule = module {
    single<StockRepository> { StockRepositoryImpl(get(), get()) }

    single<SymbolRepository> { SymbolRepositoryImpl(get(), get(), get()) }

    single<FinnApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build().create(FinnApi::class.java)
    }

    single {
        OkHttpClient.Builder().addInterceptor {
            val originalRequest = it.request()
            val urlOriginal = originalRequest.url

            val url = urlOriginal.newBuilder()
                .addQueryParameter("token", Constants.TOKEN)
                .build()

            val newRequest = originalRequest.newBuilder().url(url)
            it.proceed(newRequest.build())
        }.build()
    }

    single<SymbolsDataMapper> { SymbolsDataMapperImpl() }

    single<StockMapper> { StockMapperImpl() }

    single<SymbolEntityMapper> { SymbolEntityMapperImpl() }

    single {
        Room.databaseBuilder(
            get(),
            Database::class.java, "Database-Stocks"
        ).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("Insert Into Exchange Values ('AS', 'NYSE EURONEXT - EURONEXT AMSTERDAM')")
                    db.execSQL("Insert Into Exchange Values ('AT', 'ATHENS EXCHANGE S.A. CASH MARKET')")
                    db.execSQL("Insert Into Exchange Values ('AX', 'ASX - ALL MARKETS')")
                    db.execSQL("Insert Into Exchange Values ('BA', 'BOLSA DE COMERCIO DE BUENOS AIRES')")
                    db.execSQL("Insert Into Exchange Values ('BC', 'BOLSA DE VALORES DE COLOMBIA')")
                    db.execSQL("Insert Into Exchange Values ('BD', 'BUDAPEST STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('BE', 'BOERSE BERLIN')")
                    db.execSQL("Insert Into Exchange Values ('BK', 'STOCK EXCHANGE OF THAILAND')")
                    db.execSQL("Insert Into Exchange Values ('BO', 'BSE LTD')")
                    db.execSQL("Insert Into Exchange Values ('BR', 'NYSE EURONEXT - EURONEXT BRUSSELS')")
                    db.execSQL("Insert Into Exchange Values ('CA', 'Egyptian Stock Exchange')")
                    db.execSQL("Insert Into Exchange Values ('CN', 'CANADIAN NATIONAL STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('CO', 'OMX NORDIC EXCHANGE COPENHAGEN A/S')")
                    db.execSQL("Insert Into Exchange Values ('CR', 'CARACAS STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('DB', 'DUBAI FINANCIAL MARKET')")
                    db.execSQL("Insert Into Exchange Values ('DE', 'XETRA')")
                    db.execSQL("Insert Into Exchange Values ('DU', 'BOERSE DUESSELDORF')")
                    db.execSQL("Insert Into Exchange Values ('F', 'DEUTSCHE BOERSE AG')")
                    db.execSQL("Insert Into Exchange Values ('HE', 'NASDAQ OMX HELSINKI LTD')")
                    db.execSQL("Insert Into Exchange Values ('HK', 'HONG KONG EXCHANGES AND CLEARING LTD')")
                    db.execSQL("Insert Into Exchange Values ('HM', 'HANSEATISCHE WERTPAPIERBOERSE HAMBURG')")
                    db.execSQL("Insert Into Exchange Values ('IC', 'NASDAQ OMX ICELAND')")
                    db.execSQL("Insert Into Exchange Values ('IR', 'IRISH STOCK EXCHANGE - ALL MARKET')")
                    db.execSQL("Insert Into Exchange Values ('IS', 'BORSA ISTANBUL')")
                    db.execSQL("Insert Into Exchange Values ('JK', 'INDONESIA STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('JO', 'JOHANNESBURG STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('KL', 'BURSA MALAYSIA')")
                    db.execSQL("Insert Into Exchange Values ('KQ', 'KOREA EXCHANGE (KOSDAQ)')")
                    db.execSQL("Insert Into Exchange Values ('KS', 'KOREA EXCHANGE (STOCK MARKET)')")
                    db.execSQL("Insert Into Exchange Values ('L', 'LONDON STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('LN', 'Euronext London')")
                    db.execSQL("Insert Into Exchange Values ('LS', 'NYSE EURONEXT - EURONEXT LISBON')")
                    db.execSQL("Insert Into Exchange Values ('MC', 'BOLSA DE MADRID')")
                    db.execSQL("Insert Into Exchange Values ('ME', 'MOSCOW EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('MI', 'Italian Stock Exchange')")
                    db.execSQL("Insert Into Exchange Values ('MU', 'BOERSE MUENCHEN')")
                    db.execSQL("Insert Into Exchange Values ('MX', 'BOLSA MEXICANA DE VALORES (MEXICAN STOCK EXCHANGE)')")
                    db.execSQL("Insert Into Exchange Values ('NE', 'AEQUITAS NEO EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('NL', 'Nigerian Stock Exchange')")
                    db.execSQL("Insert Into Exchange Values ('NS', 'NATIONAL STOCK EXCHANGE OF INDIA')")
                    db.execSQL("Insert Into Exchange Values ('NZ', 'NEW ZEALAND EXCHANGE LTD')")
                    db.execSQL("Insert Into Exchange Values ('OL', 'OSLO BORS ASA')")
                    db.execSQL("Insert Into Exchange Values ('PA', 'NYSE EURONEXT - MARCHE LIBRE PARIS')")
                    db.execSQL("Insert Into Exchange Values ('PM', 'Philippine Stock Exchange')")
                    db.execSQL("Insert Into Exchange Values ('PR', 'PRAGUE STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('QA', 'QATAR EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('RG', 'NASDAQ OMX RIGA')")
                    db.execSQL("Insert Into Exchange Values ('SA', 'Brazil Bolsa - Sao Paolo')")
                    db.execSQL("Insert Into Exchange Values ('SG', 'BOERSE STUTTGART')")
                    db.execSQL("Insert Into Exchange Values ('SL', 'SINGAPORE EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('SN', 'SANTIAGO STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('SR', 'SAUDI STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('SS', 'SHANGHAI STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('ST', 'NASDAQ OMX NORDIC STOCKHOLM')")
                    db.execSQL("Insert Into Exchange Values ('SW', 'SWISS EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('SZ', 'SHENZHEN STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('T', 'TOKYO STOCK EXCHANGE-TOKYO PRO MARKET')")
                    db.execSQL("Insert Into Exchange Values ('TA', 'TEL AVIV STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('TL', 'NASDAQ OMX TALLINN')")
                    db.execSQL("Insert Into Exchange Values ('TO', 'TORONTO STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('TW', 'TAIWAN STOCK EXCHANGE')")
                    db.execSQL("Insert Into Exchange Values ('TWO', 'TPEx')")
                    db.execSQL("Insert Into Exchange Values ('US', 'US exchanges (NYSE, Nasdaq)')")
                    db.execSQL("Insert Into Exchange Values ('V', 'TSX VENTURE EXCHANGE - NEX')")
                    db.execSQL("Insert Into Exchange Values ('VI', 'Vienna Stock Exchange')")
                    db.execSQL("Insert Into Exchange Values ('VN', 'Vietnam exchanges including HOSE, HNX and UPCOM')")
                    db.execSQL("Insert Into Exchange Values ('VS', 'NASDAQ OMX VILNIUS')")
                    db.execSQL("Insert Into Exchange Values ('WA', 'WARSAW STOCK EXCHANGE/EQUITIES/MAIN MARKET')")
                    db.execSQL("Insert Into Exchange Values ('HA', 'Hanover Stock Exchange')")
                    db.execSQL("Insert Into Exchange Values ('SX', 'DEUTSCHE BOERSE Stoxx')")
                    db.execSQL("Insert Into Exchange Values ('TG', 'DEUTSCHE BOERSE TradeGate')")
                    db.execSQL("Insert Into Exchange Values ('SC', 'BOERSE_FRANKFURT_ZERTIFIKATE')")
                }
            }).build()
    }

    single { get<Database>().symbolDao() }

    single { get<Database>().exchangeDao() }
}