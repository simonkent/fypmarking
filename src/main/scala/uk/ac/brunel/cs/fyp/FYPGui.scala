package uk.ac.brunel.cs.fyp

import scala.swing.SimpleSwingApplication
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.TextField
import scala.swing.GridPanel
import java.awt.Dimension

object FYPGui extends SimpleSwingApplication {
  var markSheetReader: Option[MarkingSheetReader] = None
  
  def top = new MainFrame{
    title = "FYP Marking System"
    
    val label = new Label {
      text = "Mark Sheet Directory"
    }
    
    val markSheetDirTextBox = new TextField {
      columns = 40;
    }
    
    val gridPanel = new GridPanel(1, 2) {
      contents += label
      contents += markSheetDirTextBox
    }
    
    contents = gridPanel;
    
    size = new Dimension(300, 200)
  }
  
}