$nuget = $env:NuGet

# parse the applicationVersion number out of package.json
$bsversion = ((Get-Content $env:SourcesPath\package.json) -join "`n" | ConvertFrom-Json).applicationVersion

# create packages
& $nuget pack "nuget\bootstrap.nuspec" -Verbosity detailed -NonInteractive -NoPackageAnalysis -BasePath $env:SourcesPath -Version $bsversion
& $nuget pack "nuget\bootstrap.less.nuspec" -Verbosity detailed -NonInteractive -NoPackageAnalysis -BasePath $env:SourcesPath -Version $bsversion
