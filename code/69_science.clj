(import 'javax.measure.unit.SI)
(import 'javax.measure.Measure)

; Integer Value
; will call: 
;  public static <Q extends Quantity> Measure<java.lang.Integer, Q> valueOf
(Measure/valueOf (Integer. 2) SI/KILOGRAM)

; Long  value 
; will call: 
;  public static <Q extends Quantity> Measure<java.lang.Long, Q> valueOf
(Measure/valueOf (cast Long 2) SI/KILOGRAM)

; Float value
; will call: 
;  public static <Q extends Quantity> Measure<java.lang.Float, Q> valueOf
(Measure/valueOf (Float. 2.0) SI/KILOGRAM)