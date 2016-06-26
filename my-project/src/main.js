import Vue from 'vue'
import Hello from './components/Hello.vue'
import Landing from './components/Landing.vue'
import Router from 'vue-router'

Vue.use(Router)

// routing
var router = new Router()

router.map({
  '/hello': {
    component: Hello
  },
  '/': {
    component: Landing
  }
})

router.beforeEach(function () {
  window.scrollTo(0, 0)
})

router.start(Vue.extend({}), '#main')
