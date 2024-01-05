object Main extends App {

  private val mrns: Seq[String] = args.toList match {
    case mrnType :: number :: Nil =>
      MrnType(mrnType).generate(number.toIntOption.getOrElse {
        throw new IllegalArgumentException(s"Argument '$number' must be an integer")
      })
    case mrnType :: Nil =>
      MrnType(mrnType).generate(1)
    case _ =>
      throw new IllegalArgumentException("Arguments must include an MRN type - one of P4, P5T or P5F")
  }

  mrns.map(_.toUpperCase).foreach(println)
}