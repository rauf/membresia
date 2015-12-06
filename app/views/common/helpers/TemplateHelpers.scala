package views.common.helpers

object TemplateHelpers {

  def createRange(page: Int, max: Int, pageCount: Int): Range = {
    val middle: Int = max / 2
    val minNumbering: Int = page - (middle)
    val maxNumbering: Int = page + (middle - 1)
    
    (minNumbering, maxNumbering) match {
      case (minN, maxN) if maxN <= max && minN <= 0 => 1 to max
      case (minN, maxN) if maxN > pageCount => minN until pageCount
      case (minN, maxN) => minN to maxN
    }
  }

}
