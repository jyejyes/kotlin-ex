package com.example.lg.day6.data

data class RnDInfo( var title: String,
                    var readCnt: Int,
                    var cat : String,
                    var asocicateWord:List<String>,
                    var researcherCnt:Int=0,
                    var catIconUrl:String = ""
)

data class RnDInfoRes( var result : Boolean,
                       var msgCode : String,
                       var message : String,
                       var list : List<RnDInfo>

)