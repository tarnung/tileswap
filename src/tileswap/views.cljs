(ns tileswap.views
  (:require
    [re-frame.core :as rf]
    [tileswap.events :as events]
    [tileswap.subs :as subs]
    [tileswap.logo :refer [logo]]
    [tileswap.api-key :refer [api-key]]))

;; styles

(def flex
  { :margin "auto"
   :display "flex"
   :justify-content "center"
   :align-items "center"})

;; helper-functions

(defn register-resize-listener []
  (js/window.addEventListener
    "resize"
    #(rf/dispatch [::events/resize js/window.innerWidth js/window.innerHeight])))

(defn new-image []
  (let [img (js/Image.)
        ;; add random number to url to avoid browser-caching
        url (str "http://thecatapi.com/api/images/get?format=src&size=med&type=jpg,png&api_key=" api-key "&random=" (rand))]
    (set! (.-onload img) #(rf/dispatch [::events/new-img url (.-naturalWidth img) (.-naturalHeight img)]))
    (set! (.-onerror img) new-image)
    (set! (.-src img) url)))

(defn get-z-index [index position difficulty shift movers]
  (cond
    (and (= shift :left) (= (rem position difficulty) (dec difficulty))) 3
    (and (= shift :right) (= (rem position difficulty) 0)) 3
    (and (= shift :up) (= (js/Math.floor (/ position difficulty)) (dec difficulty))) 3
    (and (= shift :down) (= (js/Math.floor (/ position difficulty)) 0)) 3
    (= index (first movers)) 2
    (= index (second movers)) 1
    :else 0))

;; components

(defn left-button []
  (let [size (rf/subscribe [::subs/ui-size])]
    [:div
     {:style {:width 0
              :height 0
              :border-top (str @size "px solid transparent")
              :border-bottom (str @size "px solid transparent")
              :border-right (str @size "px solid black")}
      :on-click #(rf/dispatch [::events/shift :left])}]))

(defn right-button []
  (let [size (rf/subscribe [::subs/ui-size])]
    [:div
     {:style {:width 0
              :height 0
              :border-top (str @size "px solid transparent")
              :border-bottom (str @size "px solid transparent")
              :border-left (str @size "px solid black")}
      :on-click #(rf/dispatch [::events/shift :right])}]))

(defn up-button []
  (let [size (rf/subscribe [::subs/ui-size])]
    [:div
     {:style {:width 0
              :height 0
              :border-left (str @size "px solid transparent")
              :border-right (str @size "px solid transparent")
              :border-bottom (str @size "px solid black")}
      :on-click #(rf/dispatch [::events/shift :up])}]))

(defn down-button []
  (let [size (rf/subscribe [::subs/ui-size])]
    [:div
     {:style {:width 0
              :height 0
              :border-left (str @size "px solid transparent")
              :border-right (str @size "px solid transparent")
              :border-top (str @size "px solid black")}
      :on-click #(rf/dispatch [::events/shift :down])}]))

(defn puzzle-tile [index position]
  (let [difficulty (rf/subscribe [::subs/difficulty])
        selected (rf/subscribe [::subs/selected])
        shift (rf/subscribe [::subs/shift])
        movers (rf/subscribe [::subs/movers])
        transition (rf/subscribe [::subs/transition])
        img-url (rf/subscribe [::subs/img-url])
        x-pos (rem position @difficulty)
        y-pos (js/Math.floor (/ position @difficulty))
        x-img (rem index @difficulty)
        y-img (js/Math.floor (/ index @difficulty))
        scaled-img-size (rf/subscribe [::subs/scaled-img-size])
        [scaled-img-width _] @scaled-img-size
        tile-size (rf/subscribe [::subs/tile-size])
        [tile-width tile-height] @tile-size]
    [:div
     {:style    {:position              "absolute",
                 :top                   (* y-pos tile-height)
                 :left                  (* x-pos tile-width)
                 :transition            (if @transition "top 0.5s ease, left 0.5s ease")
                 :z-index               (get-z-index index position @difficulty @shift @movers)
                 :width                 tile-width
                 :height                tile-height
                 :background-image      (str "url(" @img-url ")")
                 :background-size       scaled-img-width
                 :background-repeat     "no-repeat"
                 :overflow              "hidden"
                 :opacity               (if (= index @selected) 0.5 1)
                 :background-position-x (* -1 x-img tile-width)
                 :background-position-y (* -1 y-img tile-height)}
      :on-click #(rf/dispatch [::events/tile-click index position])}]))

(defn puzzle-picture []
  (let [ui-size (rf/subscribe [::subs/ui-size])
        difficulty (rf/subscribe [::subs/difficulty])
        order (rf/subscribe [::subs/order])
        scaled-img-size (rf/subscribe [::subs/scaled-img-size])
        [scaled-img-width scaled-img-height] @scaled-img-size]
    [:div {:style {:position "relative"
                   :width    scaled-img-width
                   :height   scaled-img-height
                   :border   (str
                               (/ @ui-size 4)
                               "px solid "
                               (if (= (range (* @difficulty @difficulty)) @order) "green" "black"))}}
     (for [[index position] (map-indexed vector @order)]
       ^{:key index}
       [puzzle-tile index position])]))

(defn back-button []
  (let [size (rf/subscribe [::subs/ui-size])]
    [:a {:href "/"}
     [:div {:style    {:width            @size
                       :height           @size
                       :background-color "black"
                       :border-radius    @size
                       :cursor           "pointer"
                       :margin           "auto"
                       :display          "flex"
                       :justify-content  "center"
                       :align-items      "center"}}
      [logo "50%" "50%"]]]))

(defn difficulty-button []
  (let [size (rf/subscribe [::subs/ui-size])
        difficulty (rf/subscribe [::subs/difficulty])]
    [:input {:type      "number"
             :name      "difficulty"
             :value     (str @difficulty)
             :style     {:width           @size
                         :height          @size
                         :backgroundColor "black"
                         :borderRadius    @size
                         :color           "white"
                         :text-align      "center"
                         :font-size       (str (/ @size 3) "px")
                         :user-select     "none"}
             :on-change #(rf/dispatch [::events/set-difficulty (-> % .-target .-value js/parseInt)])}]))

(defn next-button []
  (let [size (rf/subscribe [::subs/ui-size])]
    [:div {:style {:width @size
                   :height @size
                   :background-color "black"
                   :border-radius @size
                   :color "white"
                   :text-align "center"
                   :font-size (str (/ @size 3) "px")
                   :line-height (str @size "px")
                   :cursor "pointer"}
           :on-click new-image}
     "next"]))

(defn puzzle []
  [:div {:style (into flex {:flex-direction "column"})}
   [:div {:style (into flex {:flex-direction "row"})}
    [left-button]
    [:div
     {:style (into flex {:flex-direction "column"})}
     [up-button]
     [puzzle-picture]
     [down-button]]
    [right-button]]
   [:div {:style (into flex {:flex-direction "row"})}
    [back-button]
    [difficulty-button]
    [next-button]]])

(defn main []
  (new-image)
  (register-resize-listener)
  puzzle)