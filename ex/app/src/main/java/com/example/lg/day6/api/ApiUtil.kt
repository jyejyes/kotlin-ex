
import com.example.lg.day6.BuildConfig
import com.example.lg.day6.api.CategoryService
import com.example.lg.day6.api.IssueService
import com.example.lg.day6.api.PeerService
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ApiUtil{
    private val TIME_OUT=30
    val BASE_URL: String = BuildConfig.API_SERVER_ADDR
    val ISSUE_URL="app/issue/"
    val CATEGOTY_URL="app/categoty/"
    val PEER_URL="app/peer/"

    var mIssueService: IssueService?=null
        get(){
            if(field==null){
                field==getRetrofit(BASE_URL+ ISSUE_URL).create(IssueService::class.java)
            }
            return field
        }
    var mICategoryService: CategoryService?=null
        get(){
            if(field==null){
                field==getRetrofit(BASE_URL+ CATEGOTY_URL).create(CategoryService::class.java)
            }
            return field
        }
    var mPeerService: PeerService?=null
        get(){
            if(field==null){
                field==getRetrofit(BASE_URL+ PEER_URL).create(PeerService::class.java)
            }
            return field
        }

    private fun getRetrofit(baseUrl: String): Retrofit {
        val gson = GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(Date::class.java,
                        JsonDeserializer<Date> { json, _, _ ->
                            if(json.asJsonPrimitive.isNumber) {
                                Date(json.asJsonPrimitive.asLong * 1000)
                            } else {
                                null
                            }
                        }).create()

        val client = OkHttpClient.Builder()
                .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .build()

        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
}