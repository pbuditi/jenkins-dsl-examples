def views_json = readFileFromWorkspace("hello-dsl", "views.json")
def views = new groovy.json.JsonSlurper().parseText(views_json)["views"]

views.each { view ->

	(0..4).each { i -> 

		job("${view}-dsl-example-${i}") {
			scm {
				 git("https://github.com/pbuditi/pradeep-apps.git")
			}
			steps {
				shell('echo "this is job${i}"')
			}
		}
	}



	listView(view) {

		jobs {
			regex("${view}-.*")
		}
		columns {
			status()
			weather()
			name()
			lastSuccess()
			lastFailure()
			lastDuration()
			buildButton()
		}
	}	

}
