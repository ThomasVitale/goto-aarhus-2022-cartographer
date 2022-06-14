# A Paved Path to Production on Kubernetes (GOTO Aarhus 2022)

Customers are looking forward to getting a new feature in our application. Developers implemented the
feature and pushed the changes to a Git repository. How can we go from code commit to feature
available in production on Kubernetes? How can we do that in a safe, secure, and reproducible way?

Following the principles of continuous delivery, I’ll show you how to design a paved path to
production that provides a superior experience to developers while giving operators enough 
flexibility and control. Using Cartographer, we’ll choreograph our way from code commit to production
deployment on Kubernetes and build a complete deployment pipeline (CI/CD).

Cartographer will rely on different cloud native technologies to implement the steps in the software
supply chain. We’ll monitor Git repositories with Flux, containerize applications with Cloud Native
Buildpacks and kpack, run automated tests with Tekton, scan codebases and images with Grype,
configure workloads with Carvel, and deploy them with Knative.

The presentation focuses on open-source technologies and includes a hands-on demo that you can run in your Kubernetes environments and use as a foundation for your real-world supply chains.

## Installation Instructions

### Prerequisite

Before moving to the next section, make sure you have the following tools installed:

* [kapp](https://carvel.dev/kapp/docs/v0.49.0/install/)
* [kctrl](https://carvel.dev/kapp-controller/docs/v0.38.0/install/#installing-kapp-controller-cli-kctrl)
* [Tanzu CLI](https://tanzucommunityedition.io/docs/v0.12/cli-installation/)

You'll also need a Kubernetes cluster, either locally or in the cloud.
For setting up a local environment on Kubernetes, I recommend checking out [Tanzu Community Edition](https://tanzucommunityedition.io).

### Setup the cluster

First, you'll need to install [kapp-controller](https://carvel.dev/kapp-controller/) for Kubernetes-native package management.

```shell
kapp deploy -a kapp-controller -f https://github.com/vmware-tanzu/carvel-kapp-controller/releases/download/v0.38.1/release.yml --yes
```

Then, add the following package repositories to the cluster.

```shell
kapp deploy -a cluster-setup -f platform/cluster-setup --yes
```

Finally, configure a secret with the credentials to your container registry.
Make sure you generate a dedicated authentication token and don't use your password.

```shell
tanzu secret registry add registry-credentials \
    --server REGISTRY_URL \
    --username REGISTRY_USERNAME \
    --password REGISTRY_TOKEN \
    --export-to-all-namespaces
```

* `REGISTRY_URL`: the URL for your container registry. For example, `https://ghcr.io` for GitHub Container Registry or `https://index.docker.io/v1/` for DockerHub.
* `REGISTRY_USERNAME`: the username for your container registry account.
* `REGISTRY_TOKEN`: an authentication token with write permissions to your container registry. For GitHub Container Registry, you can generate a _Personal access token_.

### Setup the platform

Let's setup the platform services.

First, go to `platform-setup/app-toolkit.yml` and fill in URL and credentials for your
container registry.

Then, install the platform services.

```shell
kapp deploy -a platform-setup -f platform/platform-setup --yes
```

## Tips

On DigitalOcean, you can create a cluster from `doctl`.

```shell
doctl k8s cluster create goto-cluster \
    --node-pool "name=basicnp;size=s-2vcpu-4gb;count=3;label=type=basic;" \
    --region ams3
```
