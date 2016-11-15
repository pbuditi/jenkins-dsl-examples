def views_json = readFileFromWorkspace("views.json")
def views = new groovy.json.JsonSlurper().parseText(views_json)["views"]

def tasks_json = readFileFromWorkspace("tasks.json")
def tasks = new groovy.json.JsonSlurper().parseText(tasks_json)["tasks"]



def gitCred = "c79906d0-32e2-41bd-bcef-5d447125a3eb"
def gitURL = "https://github.com/pbuditi/pradeep-apps.git"

views.each { view ->

	def env_json = readFileFromWorkspace("${view}.json")
	def env = new groovy.json.JsonSlurper().parseText(env_json)

	tasks.each { task -> 
		
		def ciJobName = "deploy_${view}_${task}"
		if(task == "components") {
			print "-> create components job for :${view}"
			createComponentJob(ciJobName, gitURL, gitCred, env)
		}
	}
}

def createComponentJob(def ciJobName, def gitURLL, def gitCredd, def envv) {
		job(ciJobName) {
			scm {
				git {
					remote {
				 		url(gitURLL)
				 		credentials(gitCredd)
					}
				}
			}
			steps {
				shell("echo copy components")
				shell("echo copy components /target/${envv.name}/compoments")
				
			}
	}
}



views.each { view ->
	
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