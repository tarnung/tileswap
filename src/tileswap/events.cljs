(ns tileswap.events
  (:require
    [re-frame.core :as rf]
    [tileswap.db :as db]
    [day8.re-frame.tracing :refer [fn-traced defn-traced]]))

(defn update-ui-size [db]
      (let [screen-height (-> db :screen :height)
            screen-width (-> db :screen :width)
            puzzle-scale (-> db :screen :puzzle-scale)
            ui-scale (-> db :screen :ui-scale)]
           (assoc-in db [:screen :ui-size] (* (min screen-height screen-width)
                                              puzzle-scale
                                              ui-scale))))

(defn shift-left [d i]
  (+ (* (js/Math.floor (/ i d)) d)
     (rem (+ -1 i d) d)))

(defn shift-right [d i]
  (+ (* (js/Math.floor (/ i d)) d)
     (rem (inc i) d)))

(defn shift-up [d i]
  (if (neg? (- i d))
    (+ (* d d)
       (- i d)) (- i d)))

(defn shift-down [d i]
  (rem (+ i d) (* d d)))


(rf/reg-event-db
  ::initialize-db
  (fn [_ _] db/default-db))

(rf/reg-event-db
  ::set-difficulty
  (fn [db [_ input]]
      (let [difficulty (cond
                         (< input 2) 2
                         (> input 20) 20
                         :else input)]
           (-> db
               (assoc-in [:puzzle :transition] true)
               (assoc-in [:puzzle :selected] nil)
               (assoc-in [:puzzle :movers] [])
               (assoc-in [:puzzle :difficulty] difficulty)
               (assoc-in [:puzzle :order] (into [] (shuffle (range (* difficulty difficulty)))))))))

(rf/reg-event-db
  ::resize
  (fn [db [_ width height]]
      (let [width-ratio (/ width (-> db :img :width))
            height-ratio (/ height (-> db :img :height))]
           (-> db
               (assoc-in [:puzzle :transition] false)
               (assoc-in [:screen :width] width)
               (assoc-in [:screen :height] height)
               (assoc-in [:screen :puzzle-scale] (min width-ratio height-ratio))
               update-ui-size))))

(rf/reg-event-db
  ::new-img
  (fn [db [_ url width height]]
      (let [width-ratio (/ (-> db :screen :width) width)
            height-ratio (/ (-> db :screen :height) height)]
           (-> db
               (update-in [:puzzle :order] shuffle)
               (assoc-in [:img :url] url)
               (assoc-in [:img :width] width)
               (assoc-in [:img :height] height)
               (assoc-in [:screen :puzzle-scale] (min width-ratio height-ratio))
               update-ui-size))))

(rf/reg-event-db
  ::tile-click
  (fn [db [_ index position]]
      (let [selected (-> db :puzzle :selected)
            selected-pos (-> db :puzzle :order (get selected))]
           (if (nil? selected)
             (-> db (assoc-in [:puzzle :movers] [index])
                 (assoc-in [:puzzle :selected] index))
             (-> db (assoc-in [:puzzle :transition] true)
                 (update-in [:puzzle :movers] #(conj % index))
                 (update-in [:puzzle :order] #(-> %
                                                  (assoc selected position)
                                                  (assoc index selected-pos)))
                 (assoc-in [:puzzle :selected] nil)
                 (assoc-in [:puzzle :shift] nil))))))

(rf/reg-event-db
  ::shift
  (fn [db [_ direction]]
      (let [difficulty (-> db :puzzle :difficulty)
            shift (direction {:up    shift-up
                              :down  shift-down
                              :left  shift-left
                              :right shift-right})]
           (-> db
               (assoc-in [:puzzle :transition] true)
               (update-in [:puzzle :order] (fn [order] (mapv #(shift difficulty %) order)))
               (assoc-in [:puzzle :shift] direction)))))
