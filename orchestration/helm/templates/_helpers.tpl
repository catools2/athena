{{/*
Expand the name of the chart.
*/}}
{{- define "athena.name" -}}
{{- default .Release.Name | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "athena.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "athena.labels" -}}
helm.sh/chart: {{ include "athena.chart" . }}
{{ include "athena.selectorLabels" . }}
{{ include "athena.workloadLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/part-of: athena-demo
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Workload (Pod) labels
*/}}
{{- define "athena.workloadLabels" -}}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- if .name }}
app.kubernetes.io/component: {{ .name}}
app.kubernetes.io/name: {{ .name }}
{{- end }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "athena.selectorLabels" -}}
{{- if .name }}
opentelemetry.io/name: {{ .name }}
{{- end }}
{{- end }}

{{- define "athena.envOverriden" -}}
{{- $mergedEnvs := list }}
{{- $envOverrides := default (list) .envOverrides }}

{{- range .env }}
{{-   $currentEnv := . }}
{{-   $hasOverride := false }}
{{-   range $envOverrides }}
{{-     if eq $currentEnv.name .name }}
{{-       $mergedEnvs = append $mergedEnvs . }}
{{-       $envOverrides = without $envOverrides . }}
{{-       $hasOverride = true }}
{{-     end }}
{{-   end }}
{{-   if not $hasOverride }}
{{-     $mergedEnvs = append $mergedEnvs $currentEnv }}
{{-   end }}
{{- end }}
{{- $mergedEnvs = concat $mergedEnvs $envOverrides }}
{{- mustToJson $mergedEnvs }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "athena.serviceAccountName" -}}
{{- if .serviceAccount.create }}
{{- default (include "athena.name" .) .serviceAccount.name }}
{{- else }}
{{- default "default" .serviceAccount.name }}
{{- end }}
{{- end }}

{{/*
Create a default fully qualified app name without trimming it at all.
If release name contains chart name it will be used as a full name.
This value is essentially the same as "mimir.fullname" in the upstream chart.
*/}}
{{- define "mimir.fullname" -}}
{{- if .Values.mimir.fullnameOverride -}}
{{- .Values.mimir.fullnameOverride | trunc 25 | trimSuffix "-" -}}
{{- else -}}
{{- $name := .Values.mimir.nameOverride | default ( include "mimir.infixName" . ) | trunc 25 | trimSuffix "-" -}}
{{- $releasename := .Release.Name | trunc 25 | trimSuffix "-" -}}
{{- if contains $name .Release.Name -}}
{{- $releasename -}}
{{- else -}}
{{- printf "%s-%s" $releasename $name -}}
{{- end -}}
{{- end -}}
{{- end -}}
