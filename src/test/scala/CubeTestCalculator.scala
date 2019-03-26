

import dataVectis.Exo.{cubeCalculator, prop, readFile}
import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite

class CubeTestCalculator extends FunSuite { // just a test to understand  the consept of J unit test for scala .
    test("cubeCalculator.cube"){
    val x =  new cubeCalculator
    assert(  x.cube(3) === 27  )
  }

test ("countDataRow") { // test get count method

  val Name = new prop()
  val spark = SparkSession
    .builder()
    .master("local")
    .appName(Name.getProp("NAME_APP"))
    .getOrCreate() // create a spark session connection. for Data frames ans data sets .

  val df = spark.read.format("csv").option("header" , "True").load("c:///test2.txt")
  val y = new readFile
  assert ( y.getCount(df) === 27)

}

test ("getProp") { // test get Properties.
  val z = new prop
  assert(z.getProp("NAME_APP") === "Job Spark")


}


}
