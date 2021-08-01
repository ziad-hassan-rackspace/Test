def call( action ) {
    pipeline {
        agent any
        stages {
            stage ('Run only if approval exists') {
                when {
                    expression { action }
                }
                steps {
                    echo "Performing steps as the build has been approved!!!"
                }
            }
        }
    }
}
