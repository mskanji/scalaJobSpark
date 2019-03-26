
import org.scalatest.BeforeAndAfterEach
import dataVectis.Exo.{prop, readFile}
import org.apache.spark.sql.SparkSession
import org.scalatest.FunSuite





class GivenWhenThen extends FunSuite with BeforeAndAfterEach {


   var spark : SparkSession=_

   override def beforeEach(): Unit = {

    spark = SparkSession.builder().appName("name of apps")
      .master("local")
      .getOrCreate()


  }

    test("my first Test") {
        val df = spark.read.format("csv").option("header" , "True").load("c:///test2.txt")
        val y = new readFile
        assert ( y.getCount(df) === 27)
   }


    test("test Jointure") {

      val p = new prop()


      val df1 = spark.read.format("csv").option("header" , "True").option("delimiter" , ";").load(p.getProp("DF1"))
      val df2 = spark.read.format("csv").option("header" , "True").option("delimiter" , ";").load( p.getProp("DF2")
      val df3 = spark.read.format("csv").option("header" , "True").option("delimiter" , ";").load(p.getProp("DF3")
      val x = new readFile()
      val y = x.JointureDF(df1, df2 , "id2" , "id1")
      val columns = df3.schema.fields.map(_.name)
      val selectiveDifferences = columns.map(col => df3.select(col).except(y.select(col)))
      assert( selectiveDifferences.map(diff => {if(diff.count > 0) diff.show}) !=    Array((), (), (), ())  )
    }

  override def afterEach(): Unit = {
    spark.stop()
  }

}
