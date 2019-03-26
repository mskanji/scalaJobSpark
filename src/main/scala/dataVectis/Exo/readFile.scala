package dataVectis.Exo
import java.io.{FileNotFoundException, Serializable}

import org.apache.spark.sql
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

import scala.util.Failure

class readFile {

  def read(format: String, spark: SparkSession, path: String): sql.DataFrame = {

    val df = spark.read.format(format)
      .option("header", "true")
      .load(path)
    df
  }


  def getCount(dataFrame: DataFrame) = {


    dataFrame.count()
  }


  def JointureDF(df1: DataFrame, df2: DataFrame, by_v1: String, by_v2: String): sql.DataFrame = {


    import org.apache.spark.sql.functions._

    df1.join(df2, col(by_v1) === col(by_v2))


  }

}


