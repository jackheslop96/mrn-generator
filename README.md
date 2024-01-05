# mrn-generator

This is a command line tool for generating MRNs. It allows you to specify the:
* type of MRN to produce (mandatory argument)
    * P4 - a valid MRN in P4
    * P5T - a valid MRN in P5 transition
    * P5F - a valid MRN in P5 final
* number of MRNs to produce (optional argument - default is 1)

### Examples
1. To generate 15 MRNs for P5 transition:

`sbt "runMain Main P5T 15"`

2. To generate just one MRN for P5 final:

`sbt "runMain Main P5F"`
