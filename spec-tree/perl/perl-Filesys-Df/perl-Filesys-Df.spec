Name:           perl-Filesys-Df
Version:        0.92
Release:        3%{?dist}
Summary:        Perl extension for filesystem disk space information
License:        GPL+ or Artistic
Group:          Development/Libraries
URL:            http://search.cpan.org/dist/Filesys-Df/
Source0:        http://www.cpan.org/modules/by-module/Filesys/Filesys-Df-%{version}.tar.gz
BuildRoot:      %{_tmppath}/%{name}-%{version}-%{release}-root-%(%{__id_u} -n)
BuildRequires:  perl(ExtUtils::MakeMaker)
Requires:       perl(:MODULE_COMPAT_%(eval "`%{__perl} -V:version`"; echo $version))

%description
This module provides a way to obtain filesystem disk space information.
This is a Unix only distribution. If you want to gather this information
for Unix and Windows, use Filesys::DfPortable. The only major benefit of
using Filesys::Df over Filesys::DfPortable, is that Filesys::Df supports
the use of open filehandles as arguments.

%prep
%setup -q -n Filesys-Df-%{version}
#readme is with dos EOL, convert it to unix
sed -i 's/\r//' README
 
%build
%{__perl} Makefile.PL INSTALLDIRS=vendor
make %{?_smp_mflags}

%install
rm -rf $RPM_BUILD_ROOT

make pure_install PERL_INSTALL_ROOT=$RPM_BUILD_ROOT

find $RPM_BUILD_ROOT -type f -name .packlist -exec rm -f {} \;
find $RPM_BUILD_ROOT -type f -name '*.bs' -empty -exec rm -f {} \;
find $RPM_BUILD_ROOT -depth -type d -exec rmdir {} 2>/dev/null \;

%{_fixperms} $RPM_BUILD_ROOT/*

%check
make test

%clean
rm -rf $RPM_BUILD_ROOT

%files
%defattr(-,root,root,-)
%doc Changes README
%{perl_vendorarch}/*
%{_mandir}/man3/*

%changelog
* Wed Jul 30 2008 Miroslav Suchy <msuchy@redhat.com> 0.92-3
- Add build dependency on MakeMaker

* Thu Jul 24 2008 Miroslav Suchy <msuchy@redhat.com> 0.92-2 
- fixed README end of lines.
- remove zero sized *.bs files.

* Tue Jul 15 2008 Miroslav Suchy <msuchy@redhat.com> 0.92-1
- Specfile autogenerated by cpanspec 1.77.
