# 1.aladdin

a flow-based scheduling algorithm, we will share our flow-based algorithm later.
Note that this project is a prototype.

Now it supports:

- priority
- overprovisioning

# 2.usage

## 2.1 apply a algorithm

```
kubectl apply -f queue-scheduler-cm.yaml
```

## 2.2 start aladdin

set the following environment variables 

```
kubeUrl: https://master-ip:6443
token: see https://github.com/kubesys/client-java
schedulerName: aladdin
schedulerType: queue 
```

## 2.3 run an example

```
{
  "apiVersion": "v1",
  "kind": "Pod",
  "metadata": {
    "name": "busybox",
    "namespace": "default"
  },
  "spec": {
    "schedulerName": "aladdin",
    "containers": [
      {
        "image": "busybox",
        "command": [
          "sleep",
          "3600"
        ],
        "imagePullPolicy": "IfNotPresent",
        "name": "busybox"
      }
    ],
    "restartPolicy": "Always"
  }
}
```
