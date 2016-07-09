import Vue from 'vue'
import Router from 'vue-router'

import App from './App.vue'
import Home from './components/Home.vue'
import Example from './components/Example.vue'

Vue.use(Router)

const router = new Router()

router.map({
  '/': {
    component: Home,
    name: 'home'
  },
  'example': {
    component: Example,
    name: 'example'
  }
})

router.start(App, document.body)
