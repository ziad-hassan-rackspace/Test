def call() {
    pipeline {
        agent any
        stages {
            stage ('Run only if approval exists') {
                when {
                    expression { uatInput.buildIsUatApproved() }
                }
                steps {
                    echo "Performing steps as the build has been approved!!!"
                }
            }
        }
    }
}
