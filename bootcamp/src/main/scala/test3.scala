import dataanan._
import spinal.core._
import spinal.lib._


case class MyBus(payloadWidth: Int) extends Bundle with IMasterSlave {
  val valid   = Bool()
  val ready   = Bool()
  val payload = Bits(payloadWidth bits)

  // define the direction of the data in a master mode
  override def asMaster(): Unit = {
    out(valid, payload)
    in(ready)
  }

  // Connect that to this
  def <<(that: MyBus): Unit = {
    this.valid   := that.valid
    that.ready   := this.ready
    this.payload := that.payload
  }

  // Connect this to the FIFO input, return the fifo output
  def queue(size: Int): MyBus = {
    val fifo = new MyBusFifo(payloadWidth, size)
    fifo.io.push << this
    return fifo.io.pop
  }
}

class MyBusFifo(payloadWidth: Int, depth: Int) extends Component {
  val io = new Bundle {
    val push = slave(MyBus(payloadWidth))
    val pop  = master(MyBus(payloadWidth))
  }
  io.pop <> io.push
}

class Top extends Component {
  val io = new Bundle {
    val idata = slave(MyBus(8))
    val odata  = master(MyBus(8))
  }
  io.odata << io.idata.queue(32)
}
object Top3{
  def main(args: Array[String]): Unit = {
    val rtl=SpinalVerilog(new Top)
//    val letread = new readsystem(rtl.toplevel)
//    letread.beginread
//    val letdraw = new draw(rtl.toplevel)
//    letdraw.begindraw
    val datann=new ModuleAnalyzer(rtl.toplevel)
    val cells=datann.getCells(_=>true)
    val pins=datann.getPins(_=>true)
    while(cells.nonEmpty){
      println(cells.head.children)
      cells.head.children.insert()
      cells.remove(cells.head)
    }
    while(pins.nonEmpty){
      println(pins.head.getComponent().children)
      pins.remove(pins.head)
    }
  }
}
