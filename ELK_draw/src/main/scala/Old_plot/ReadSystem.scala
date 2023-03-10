package Old_plot
import spinal.core._
import spinal.lib.tools.ModuleAnalyzer


class ReadSystem(rtl: SpinalReport[Component]) extends App {
//  def readfans(sets:mutable.LinkedHashSet[BaseType]): Unit ={
//    while (!sets.isEmpty) {
//      val fans = new DataAnalyzer(sets.head)
//      println(sets.head + ":\n")
//      if(fans.getFanIn.nonEmpty){
//        val fanin = fans.getFanIn.head.getPartialName()
//        println("    扇入：" + fanin+ "\n")
//      }
//      if(fans.getFanOut.nonEmpty){
//        val fanout = fans.getFanOut.head.getPartialName()
//        println("    扇出：" + fanout + "\n")
//      }
//      sets.remove(sets.head)
//    }
//  }
  def beginread: Unit = {
    val module=rtl.toplevel
    val groupIO=module.getGroupedIO(true)
  for(sss<-groupIO){
    println(sss)
    println(sss.flatten)
  }
  val components=module.children
//  println(components)
//  for(elem<-components){
//    val mmIO=elem.getGroupedIO(true)
//    println(mmIO)
//  }
//    println(s"GroupIO"+groupIO+"\n")
//    for(element<-groupIO){
//      println(element+":")
//      println(element.flatten+"\n")
////      val dataana=new DataAnalyzer(element.flatten)
//    }
    val anal=new ModuleAnalyzer(module)
//    val Ip = anal.getInputs
//  for(iji<-Ip){
//    println(iji)
//    println(iji.flatten)
//  }

    val Rg = anal.getRegisters
    println("寄存器："+Rg+"\n")
//    val Op = anal.getOutputs
//    println("输出："+Op+"\n")
//    val Cl=anal.getClocks
//    println("时钟："+Cl+"\n")
//    val Ce=anal.getCells(_=>true)
//    println("cells："+Ce+"\n")
//    val LibCe = anal.getCells(_ => true)
//    println("Libcells：" + LibCe + "\n")
    val Ne=anal.getNets(_=>true)
    println("wires："+Ne+"\n")
    val Pi = anal.getPins(_ => true)
    println("Pins：" + Pi + "\n")
  val allNets=anal.getRegisters++anal.getPins(_=>true)
  println(allNets)
////    readfans(Pi)
//    val LibPi = anal.getLibPins(_ => true)
////    readfans(LibPi)
//    println("LibPins：" + LibPi + "\n")
//    val Po = anal.getPorts(_ => true)
//    println("Ports：" + Po + "\n")

  }
}
