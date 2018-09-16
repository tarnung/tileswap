(ns tileswap.logo)

(def base-size 90)
(def stroke-width 1)

(def logo-constants
  {:base base-size
   :stroke-width stroke-width
   :crevasse (* base-size (/ 2 30))
   :bar (* base-size (/ 18 30))
   :bar-base2 (* base-size (/ 4 30))
   :bar-connector (- (* base-size (/ 4 30)) stroke-width)
   :t-bar (* base-size (/ 18 30))
   :t-column (* base-size (/ 18 30))
   :above-bar (* base-size (/ 17.5 30))
   :left-above-bar (* base-size (/ 9 30))
   :left-low-bar (* base-size (/ 9 30))
   :left-lower-bar (* base-size (/ 13 30))
   :base-color "rgb(130,80,40)"
   :hole-color "rgb(140,160,110)"
   :bar-color "rgb(245,60,55)"
   :stroke-color "rgb(0,0,0)"})

(defn base []
      [:rect {:width (:base logo-constants)
              :height (:base logo-constants)
              :fill (:base-color logo-constants)
              :stroke-width (:stroke-width logo-constants)
              :stroke (:stroke-color logo-constants)}])

(defn crevasse-through []
      [:rect {:width (:crevasse logo-constants)
              :height (:base logo-constants)
              :x (- (* (:base logo-constants) (/ 2 3)) (/ (:crevasse logo-constants) 2))
              :fill (:hole-color logo-constants)
              :stroke-width (:stroke-width logo-constants)
              :stroke (:stroke-color logo-constants)}])

(defn crevasse-left []
      [:rect {:width (/ (:base logo-constants) 2)
              :height (:crevasse logo-constants)
              :y (- (* (:base logo-constants) (/ 2 3)) (/ (:crevasse logo-constants) 2))
              :fill (:hole-color logo-constants)
              :stroke-width (:stroke-width logo-constants)
              :stroke (:stroke-color logo-constants)}])

(defn hole []
      [:circle {
                :cx (/ (:base logo-constants) 2)
                :cy (/ (:base logo-constants) 2)
                :r (/ (* (:base logo-constants) (/ 21 30)) 2)
                :stroke (:stroke-color logo-constants)
                :stroke-width (:stroke-width logo-constants)
                :fill (:hole-color logo-constants)}])

(defn bar-base []
      [:rect {:width (:crevasse logo-constants)
              :height (:bar logo-constants)
              :x (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ 2 30))
                    (/ (:crevasse logo-constants) 2))
              :y (- (/ (:base logo-constants) 2) (/ (:bar logo-constants) 2))
              :fill (:bar-color logo-constants)
              :stroke-width (:stroke-width logo-constants)
              :stroke (:stroke-color logo-constants)}])

(defn bar-base2 []
      [:rect {
              :width (:crevasse logo-constants)
              :height (:bar-base2 logo-constants)
              :x (- (/ (:base logo-constants)  2)
                    (* (:base logo-constants) (/ 4 30))
                    (/ (:crevasse logo-constants) 2))
              :y (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ -2 30))
                    (/ (:bar-base2 logo-constants) 2))
              :fill (:bar-color logo-constants)
              :stroke-width (:stroke-width logo-constants)
              :stroke (:stroke-color logo-constants)}])

(defn bar-base-connector []
      [:rect {:width (:crevasse logo-constants)
              :height (:bar-connector logo-constants)
              :x (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ 3 30))
                    (* (:stroke-width logo-constants) -1)
                    (/ (:crevasse logo-constants) 2))
              :y (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ -2 30))
                    (/ (:bar-connector logo-constants) 2))
              :fill (:bar-color logo-constants)}])

(defn base-bar []
      [:g
       [bar-base]
       [bar-base2]
       [bar-base-connector]])

(defn t-bar []
      [:rect {
              :width (:t-bar logo-constants)
              :height (:crevasse logo-constants)
              :x (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ -7 30))
                    (/ (:t-bar logo-constants) 2))
              :y (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ 3 30))
                    (/ (:crevasse logo-constants) 2))
              :fill (:bar-color logo-constants)
              :stroke-width (:stroke-width logo-constants)
              :stroke (:stroke-color logo-constants)}])

(defn t-column []
      [:rect {
              :width (:crevasse logo-constants)
              :height (:t-column logo-constants)
              :x (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ -7 30))
                    (/ (:crevasse logo-constants) 2))
              :y (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ -7 30))
                    (/ (:t-column logo-constants) 2))
              :fill (:bar-color logo-constants)
              :stroke-width (:stroke-width logo-constants)
              :stroke (:stroke-color logo-constants)}])

(defn t-above []
      [:rect {
              :width (:above-bar logo-constants)
              :height (:crevasse logo-constants)
              :x (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ -6.75 30))
                    (/ (:above-bar logo-constants) 2))
              :y (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ 6 30))
                    (/ (:crevasse logo-constants) 2))
              :fill (:bar-color logo-constants)
              :stroke-width (:stroke-width logo-constants)
              :stroke (:stroke-color logo-constants)}])

(defn t-connector []
      [:rect {
              :width (- (:crevasse logo-constants)
                        (:stroke-width logo-constants))
              :height (:crevasse logo-constants)
              :x (- (/ (:base logo-constants)  2)
                    (* (:base logo-constants) (/ -7 30))
                    (/ (:stroke-width logo-constants) -2)
                    (/ (:crevasse logo-constants) 2))
              :y (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ 2 30))
                    (* (:stroke-width logo-constants) -1)
                    (/ (:crevasse logo-constants) 2))
              :fill (:bar-color logo-constants)}])

(defn t []
      [:g
       [t-bar]
       [t-column]
       [t-connector]])

(defn bar-left-above []
      [:rect {
              :width (:left-above-bar logo-constants)
              :height (:crevasse logo-constants)
              :x (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ 8 30))
                    (/ (:left-above-bar logo-constants) 2))
              :y (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ -2 30))
                    (/ (:crevasse logo-constants) 2))
              :fill (:bar-color logo-constants)
              :stroke-width (:stroke-width logo-constants)
              :stroke (:stroke-color logo-constants)}])

(defn left-low []
      [:rect {
              :width (:left-low-bar logo-constants)
              :height (:crevasse logo-constants)
              :x (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ 10.75 30))
                    (/ (:left-low-bar logo-constants) 2))
              :y (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ -10.5 30))
                    (/ (:crevasse logo-constants) 2))
              :fill (:bar-color logo-constants)
              :stroke-width (:stroke-width logo-constants)
              :stroke (:stroke-color logo-constants)}])

(defn left-lower []
      [:rect {
              :width (:left-lower-bar logo-constants)
              :height (:crevasse logo-constants)
              :x (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ 9 30))
                    (/ (:left-lower-bar logo-constants) 2))
              :y (- (/ (:base logo-constants) 2)
                    (* (:base logo-constants) (/ -13.5 30))
                    (/ (:crevasse logo-constants) 2))
              :fill (:bar-color logo-constants)
              :stroke-width (:stroke-width logo-constants)
              :stroke (:stroke-color logo-constants)}])

(defn logo [width height]
      [:svg {:version "1.1"
             :base-profile "full"
             :xmlns "http://www.w3.org/2000/svg"
             :view-box "0 0 100 100"
             :style {:display "block"
                     :width width
                     :height height
                     :margin-left "auto"
                     :margin-right "auto"}}
       [:g {:transform "translate(5, 5)"}
        [base]
        [crevasse-through]
        [crevasse-left]
        [hole]
        [base-bar]
        [t]
        [t-above]
        [bar-left-above]
        [left-low]
        [left-lower]]])