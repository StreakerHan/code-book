part of dashboard;

class _Member extends StatelessWidget {
  const _Member({
    required this.member,
    Key? key,
  }) : super(key: key);

  final List<String> member;
  final int maxDisplayPeople = 2;

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: List.generate(
        (member.length > maxDisplayPeople) ? maxDisplayPeople : member.length,
        (index) => SimpleUserProfile(
          name: member[index],
          onPressed: () async {
            if (Platform.isIOS) {
              await LaunchApp.openApp(
                // androidPackageName: 'com.baidu.netdisk',
                iosUrlScheme: 'flutterdemos://',
                appStoreLink:
                'itms-apps://itunes.apple.com/us/app/pulse-secure/id6444874649',
                openStore: true
              );
            }
          },
        ),
      ).toList(),
    );
  }
}
