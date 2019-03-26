package dataVectis.Exo
import org.apache.spark.sql.SparkSession


object SNCF extends Serializable {

  def main(args: Array[String]): Unit = {

    val Name = new prop()
    val spark = SparkSession
      .builder()
      .master("local")
      .appName(Name.getProp("NAME_APP"))
      .getOrCreate() // create a spark session connection. for Data frames ans data sets .


    val pathTrain = new prop
    println(pathTrain.getProp( "PATH_TRAIN" )  )
    val readData = new readFile()
    val df_Train = readData.read("csv",spark,pathTrain.getProp("PATH_TRAIN"))
    val df_Travel = readData.read("csv",spark,pathTrain.getProp("PATH_TRAVEL"))
    println( "count lines DATA TRAIN = " + readData.getCount(df_Train)) // test dataFrame.
    println("count lines DATA TRAVEL = " + readData.getCount(df_Travel))

    df_Train.show()  // showing Data!
    df_Travel.show()
    import spark.implicits._ // call data frame API .
    df_Train.join(df_Travel , $"train_id" === $"id_train1"  ).show() // jointure!! mdfker
   //  ou bien
   var dfJoined = readData.JointureDF(df_Train ,df_Travel,"train_id" ,  "id_train1"  )
    dfJoined.filter( $"status1"=== "LGV" ).filter($"status"==="On Time").orderBy("speed").show()
    dfJoined.createOrReplaceTempView("dfSQL")
    spark.sql ( " select * from dfSQL where status1 =='LGV' AND status == 'On Time'  order by speed  " ).show(5)
    println("number of LGV not late" + dfJoined.filter($"status1" =!= "On Time" ).count( ) )


// Doing staff with DATASETS.


    println (" joined Data Set \n ===========================================================================")
    val trainDs = df_Train.as[Train]
    val travelDs = df_Travel.as[Travel]
    trainDs.show()
    println("===========================================================================")
    travelDs.show()
     val joinedDS=  travelDs.joinWith(trainDs , travelDs( "train_id")===trainDs("id_train1"))
     println (" joined Data Set \n ===========================================================================>")

    //LGV ON TIME MAX SPEED
     joinedDS.show(5)
     joinedDS.printSchema()
     joinedDS.filter( x => x._1.status == "On Time")
       .filter(x => x._2.status1 == "LGV").sort($"_1.speed").select($"_1.train_id" ). show(5)

     // ON TIME NUMBER
    joinedDS
         .filter(x => x._1.status == "On Time")
         .sort($"_1.nombre")
         .show(5)


    println( "the fastest LGV On Time is \n =================================================================> \n" )


       //.filter($"_._1.status" === "On Time").sort($"_._1.speed")     .show()
    //joinedDS.filter()
  }
}
