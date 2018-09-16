(ns tileswap.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [tileswap.events :as events]
   [tileswap.views :as views]
   [tileswap.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
