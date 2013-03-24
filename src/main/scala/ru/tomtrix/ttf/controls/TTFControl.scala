package ru.tomtrix.ttf.controls

import ru.tomtrix.ttf._
import ru.tomtrix.ttf.Controls._
import ru.tomtrix.ttf.Implicits._
import ru.tomtrix.ttf.controls.StdControlContainer._
import org.eclipse.swt.SWT._
import org.eclipse.swt.events._
import org.eclipse.swt.widgets._
import org.eclipse.swt.layout.FillLayout

abstract class Parameters {
  var parent: Composite
  var style: Int
  var x: Int
  var y: Int
  var width: Int
  var height: Int
  var title: String
  var compositeStyle: Int
}

case class SingleParameters(var parent: Composite, var x: Int, var y: Int, text: String) extends Parameters {
  var style = NONE
  var width = -1
  var height = -1
  var title = ""
  var compositeStyle = NONE
  var msg = ""
  var state: CHECKSTATE = UNCHECKED
  var items = Seq("")
}

case class MultiParameters(var parent: Composite, var x: Int, var y: Int, texts: Seq[String]) extends Parameters {
  var style = NONE
  var width = 120
  var height = 28
  var title = ""
  var compositeStyle = NONE
  var margin = 10
}

abstract class TTFControl[+T <% Textable] {
  val container: Composite
  val control: T
  def getResult: String
}

abstract class TTFControls[+T <% Textable] {
  val container: Composite
  val controls: Seq[T]
  def getResult: String
}

/**
 * sr
 */
object TTFControl {


  private def getSingleBase[T <% Textable](control: T, container: Composite, p: SingleParameters, mode: Int = INHERIT_DEFAULT) (addListener: T => Unit) = {
    if (control==null) throw new IllegalArgumentException("Null control")
    container setBackground p.parent.getBackground
    container setBackgroundMode mode
    container setLayout new FillLayout
    control setText p.text
    addListener(control)
    control setBounds (0, 0, p.width, p.height)
    container setLocation (p.x, p.y)
    container setSize container.computeSize(p.width, p.height)
    (container, control)
  }

  private def getMultiBase[T <% Textable](controls: Seq[T], container: Composite, p: MultiParameters, mode: Int = INHERIT_DEFAULT) (addListener: T => Unit) = {
    if (controls.isEmpty) throw new IllegalArgumentException("Empty list")
    container setBackground p.parent.getBackground
    container setBackgroundMode mode
    controls zip p.texts foreach { t => t._1 setText t._2 }
    controls foreach addListener
    controls.zipWithIndex foreach { t => t._1 setBounds(p.margin, t._2*p.height + 2*p.margin, p.width, p.height) }
    val maxX = controls maxBy {c => c.getLocation().x + c.getSize().x}
    val maxY = controls maxBy {c => c.getLocation().y + c.getSize().y}
    container setBounds (p.x, p.y, maxX.getLocation().x + maxX.getSize().x + p.margin, maxY.getLocation().y + maxY.getSize().y + p.margin)
    (container, controls)
  }

  private def getComposite(p: Parameters) = {
    if (p.title === "") new Composite(p.parent, p.compositeStyle) else {
      val g = new Group(p.parent, p.compositeStyle)
      g setText p.title
      g
    }
  }

  def createButton(p: SingleParameters)(onClick: SelectionEvent => Unit) = {
    if (p.width < 0) p.width = STD_WIDTH
    val composite = getComposite(p)
    val (cont, btn) = getSingleBase(new Button(composite, p.style), composite, p, INHERIT_FORCE) {
      _.addSelectionListener(new SelectionAdapter {
        override def widgetSelected(e: SelectionEvent) { onClick(e) }
      })
    }
    new TTFControl[Button] {
      val container = cont
      val control = btn
      def getResult = btn.getSelection toString()
    }
  }

  def createCheckbox(p: SingleParameters) (onClick: SelectionEvent => Unit) = {
    val composite = getComposite(p)
    val control = new Button(composite, p.style | CHECK)
    p.state match {
      case CHECKED => control setSelection true
      case UNCHECKED => control setSelection false
      case GRAYED => control setSelection true; control setGrayed true
    }
    val (cont, chkbox) = getSingleBase(control, composite, p) {
      _.addSelectionListener(new SelectionAdapter {
        override def widgetSelected(e: SelectionEvent) { onClick(e) }
      })
    }
    new TTFControl[Button] {
      val container = cont
      val control = chkbox
      def getResult = if (chkbox getSelection()) {
        if (chkbox getGrayed()) GRAYED toString()
        else CHECKED toString()
      }
      else UNCHECKED toString()
    }
  }

  def createTextbox(p: SingleParameters) (onModify: ModifyEvent => Unit) = {
    if (p.width < 0) p.width = STD_WIDTH
    val composite = getComposite(p)
    val (cont, txt) = getSingleBase(new Text(composite, p.style | SINGLE | BORDER), composite, p) {
      _.addModifyListener(new ModifyListener {
        def modifyText(e: ModifyEvent) { onModify(e) }
      })
    }
    new TTFControl[Text] {
      val container = cont
      val control = txt
      def getResult = txt getText()
    }
  }

  def createCombobox(p: SingleParameters) (onModify: ModifyEvent => Unit) = {
    if (p.width < 0) p.width = STD_WIDTH
    val composite = getComposite(p)
    val control = new Combo(composite, p.style)
    control setItems p.items.toArray
    val (cont, combo) = getSingleBase(control, composite, p) {
      _.addModifyListener(new ModifyListener {
        def modifyText(e: ModifyEvent) { onModify(e) }
      })
    }
    new TTFControl[Combo] {
      val container = cont
      val control = combo
      def getResult = combo getText()
    }
  }

  def createRadiobuttons(p: MultiParameters)(onClick: SelectionEvent => Unit) = {
    val composite = getComposite(p)
    val (cont, radiobtns) = getMultiBase(p.texts map {t => new Button(composite, p.style | RADIO)}, composite, p) {
      _.addSelectionListener(new SelectionAdapter {
        override def widgetSelected(e: SelectionEvent) { onClick(e) }
      })
    }
    new TTFControls[Button] {
      val container = cont
      val controls = radiobtns
      def getResult = (radiobtns.zipWithIndex find {_._1 getSelection()} map {_._2} getOrElse -1) toString()
    }
  }
}
